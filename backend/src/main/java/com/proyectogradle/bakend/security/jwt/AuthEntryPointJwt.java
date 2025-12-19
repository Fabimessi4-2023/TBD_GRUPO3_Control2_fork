package com.proyectogradle.bakend.security.jwt;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Punto de entrada para la autenticación JWT.
 *
 * Esta clase personaliza la respuesta cuando un usuario no autenticado intenta acceder
 * a un endpoint protegido por seguridad (Spring Security).
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // Imprime el error en consola para depuración
        System.err.println("Unauthorized error: " + authException.getMessage());
        // Envía al cliente una respuesta HTTP 401 (No autorizado) con un mensaje personalizado
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: No autorizado");
    }
}