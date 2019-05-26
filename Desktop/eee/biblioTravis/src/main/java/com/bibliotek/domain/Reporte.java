package com.bibliotek.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Reporte.
 */
@Entity
@Table(name = "reporte")
public class Reporte implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha")
    private LocalDate fecha;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("reportes")
    private Estudiante estudiante;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("reportes")
    private Biblioteca biblioteca;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("reportes")
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

    public Reporte nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Reporte descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Reporte fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Reporte estudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
        return this;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public Reporte biblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        return this;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public SalaEstudio getSalaEstudio() {
        return salaEstudio;
    }

    public Reporte salaEstudio(SalaEstudio salaEstudio) {
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
        Reporte reporte = (Reporte) o;
        if (reporte.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reporte.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reporte{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
