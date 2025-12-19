package com.proyectogradle.bakend.DTO;

/**
 * DTO para la petición de registro de usuario.
 * Esta clase sirve como modelo de datos para recibir información desde el frontend
 * cuando un nuevo usuario se registra en el sistema.
 */
public class SignupRequest {
    private String username;

    private String password;

    private double latitud;
    private double longitud;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}