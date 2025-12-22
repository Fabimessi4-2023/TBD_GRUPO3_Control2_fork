package com.proyectogradle.bakend.controllers;

import com.proyectogradle.bakend.DTO.LoginRequest;
import com.proyectogradle.bakend.DTO.MensajeRespuesta;
import com.proyectogradle.bakend.DTO.SignupRequest;
import com.proyectogradle.bakend.entities.Usuario;
import com.proyectogradle.bakend.repository.UserRepository;
import com.proyectogradle.bakend.security.JwtUtils;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository usuarioRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    // ----------------- LOGIN -----------------
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        if (username == null || username.isBlank()) {
            return ResponseEntity.badRequest().body(new MensajeRespuesta("Error: username is required"));
        }

        Optional<Usuario> userOptional = usuarioRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MensajeRespuesta("Error: Invalid username or password!"));
        }

        // Autenticación con Spring Security (se usa el username y la password que llega
        // en el request)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username, loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        Usuario usuario = userOptional.get();

        return ResponseEntity.ok(Map.of(
                "token", jwt,
                "id", usuario.getId(),
                "username", usuario.getUsername(),
                "creadoEn", usuario.getCreadoEn() // asume LocalDateTime creadoEn en la entidad
        ));
    }

    // ----------------- SIGNUP -----------------
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        // 1. Validaciones básicas
        if (signUpRequest.getUsername() == null || signUpRequest.getUsername().isBlank()) {
            return ResponseEntity.badRequest().body(new MensajeRespuesta("Error: username is required"));
        }

        if (usuarioRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MensajeRespuesta("Error: Username is already in use!"));
        }

        // 2. Preparar la geometría (PostGIS)
        // Usamos SRID 4326 (estándar GPS/WGS84)
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        // IMPORTANTE: El orden es (Longitud, Latitud)
        Point puntoUbicacion = geometryFactory.createPoint(
                new Coordinate(signUpRequest.getLongitud(), signUpRequest.getLatitud()));

        // 3. Mapear datos a la Entidad
        Usuario usuario = new Usuario();
        usuario.setUsername(signUpRequest.getUsername());
        usuario.setPasswordHash(encoder.encode(signUpRequest.getPassword()));
        usuario.setUbicacion(puntoUbicacion); // Campo Geoespacial
        usuario.setCreadoEn(LocalDateTime.now());

        // 4. Guardar
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(new MensajeRespuesta("User registered successfully with location!"));
    }
}