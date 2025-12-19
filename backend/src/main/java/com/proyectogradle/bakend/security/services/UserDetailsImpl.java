package com.proyectogradle.bakend.security.services;
import com.proyectogradle.bakend.entities.Usuario;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;

/**
 * Implementación de UserDetails para Spring Security.
 * Adaptador entre la entidad Usuario y el sistema de autenticación.
 */
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String username;
    private String email;

    @JsonIgnore
    private String password;
    // Lista de permisos/roles (GrantedAuthority) que tiene el usuario
    private Collection<? extends GrantedAuthority> authorities;

    // Constructor completo
    public UserDetailsImpl(Integer id, String username, String email, String firstName,
                           String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // Método estático para construir la instancia desde Usuario
    public static UserDetailsImpl build(Usuario usuario) {
        return new UserDetailsImpl(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getUsername(), //
                null,                   // firstName no está en la entidad
                usuario.getPasswordHash(),
                java.util.Collections.emptyList()
        );
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    // Métodos requeridos por la interfaz UserDetails
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    // Estos métodos controlan el estado de la cuenta (si está expirada, bloqueada, etc.)
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}