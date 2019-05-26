package com.bibliotek.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A NotificacionInfo.
 */
@Entity
@Table(name = "notificacion_info")
public class NotificacionInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("notificacionInfos")
    private Bibliotecario bibliotecario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("notificacionInfos")
    private Administrativo administrativo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("notificacionInfos")
    private Biblioteca biblioteca;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("notificacionInfos")
    private SalaEstudio salaEstudio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public NotificacionInfo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public NotificacionInfo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public NotificacionInfo fechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public NotificacionInfo fechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public NotificacionInfo bibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
        return this;
    }

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public Administrativo getAdministrativo() {
        return administrativo;
    }

    public NotificacionInfo administrativo(Administrativo administrativo) {
        this.administrativo = administrativo;
        return this;
    }

    public void setAdministrativo(Administrativo administrativo) {
        this.administrativo = administrativo;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public NotificacionInfo biblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        return this;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public SalaEstudio getSalaEstudio() {
        return salaEstudio;
    }

    public NotificacionInfo salaEstudio(SalaEstudio salaEstudio) {
        this.salaEstudio = salaEstudio;
        return this;
    }

    public void setSalaEstudio(SalaEstudio salaEstudio) {
        this.salaEstudio = salaEstudio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NotificacionInfo notificacionInfo = (NotificacionInfo) o;
        if (notificacionInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notificacionInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NotificacionInfo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            "}";
    }
}
