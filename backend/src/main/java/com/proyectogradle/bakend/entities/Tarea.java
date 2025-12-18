package com.proyectogradle.bakend.entities;

import jakarta.persistence.*;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarea")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(nullable = false)
    private Boolean completada = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sector")
    private Sector sector;

    @Column(columnDefinition = "geography(Point, 4326)", nullable = false)
    private Point ubicacion;

    @Column(name = "creada_en", updatable = false, insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime creadaEn;

    public Tarea() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public Boolean getCompletada() { return completada; }
    public void setCompletada(Boolean completada) { this.completada = completada; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Sector getSector() { return sector; }
    public void setSector(Sector sector) { this.sector = sector; }

    public Point getUbicacion() { return ubicacion; }
    public void setUbicacion(Point ubicacion) { this.ubicacion = ubicacion; }

    public LocalDateTime getCreadaEn() { return creadaEn; }
    public void setCreadaEn(LocalDateTime creadaEn) { this.creadaEn = creadaEn; }
}