package com.proyectogradle.bakend.entities;

import jakarta.persistence.*;

import java.awt.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class Usuario {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false, length = 100)
        private String username;

        @Column(name = "password_hash", nullable = false)
        private String passwordHash;

        @Column(columnDefinition = "geography(Point, 4326)", nullable = false)
        private Point ubicacion;

        @Column(name = "creado_en", updatable = false, insertable = false,
                columnDefinition = "TIMESTAMP DEFAULT NOW()")
        private LocalDateTime creadoEn;

        // Getters y Setters

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getPasswordHash() {
                return passwordHash;
        }

        public void setPasswordHash(String passwordHash) {
                this.passwordHash = passwordHash;
        }

        public Point getUbicacion() {
                return ubicacion;
        }

        public void setUbicacion(Point ubicacion) {
                this.ubicacion = ubicacion;
        }

        public LocalDateTime getCreadoEn() {
                return creadoEn;
        }

        public void setCreadoEn(LocalDateTime creadoEn) {
                this.creadoEn = creadoEn;
        }
}
