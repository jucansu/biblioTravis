package com.bibliotek.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import io.swagger.annotations.ApiModelProperty;

/**
 * A Estudiante.
 */
@Entity
@Table(name = "estudiante")
public class Estudiante implements Serializable {

	public Boolean getPausa() {
		return pausa;
	}

	public void setPausa(Boolean pausa) {
		this.pausa = pausa;
	}

	public Instant getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Instant fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@Nullable
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Column(name = "id_biblioteca_esta")
	private Long idBibliotecaEsta;

	@Column(name = "codigo_qr")
	private String codigoQR;

	@Column(name = "pausa")
	private Boolean pausa;

	@Column(name = "fecha_modificacion")
	private Instant fechaModificacion;

	public String getCodigoQR() {
		return codigoQR;
	}

	public void setCodigoQR(String codigoQR) {
		this.codigoQR = codigoQR;
	}

	/**
	 * Estudiante
	 */
	@ApiModelProperty(value = "Estudiante")
	@OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
	private Set<Reporte> reportes = new HashSet<>();
	@OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
	private Set<Biblioteca> bibliotecas = new HashSet<>();
	@OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
	private Set<Aviso> avisos = new HashSet<>();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getIdBibliotecaEsta() {
		return idBibliotecaEsta;
	}

	public void setIdBibliotecaEsta(Long idBibliotecaEsta) {
		this.idBibliotecaEsta = idBibliotecaEsta;
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Reporte> getReportes() {
		return reportes;
	}

	public Estudiante reportes(Set<Reporte> reportes) {
		this.reportes = reportes;
		return this;
	}

	public Estudiante addReporte(Reporte reporte) {
		this.reportes.add(reporte);
		reporte.setEstudiante(this);
		return this;
	}

	public Estudiante removeReporte(Reporte reporte) {
		this.reportes.remove(reporte);
		reporte.setEstudiante(null);
		return this;
	}

	public void setReportes(Set<Reporte> reportes) {
		this.reportes = reportes;
	}

	public Set<Biblioteca> getBibliotecas() {
		return bibliotecas;
	}

	public Estudiante bibliotecas(Set<Biblioteca> bibliotecas) {
		this.bibliotecas = bibliotecas;
		return this;
	}

	public Estudiante addBiblioteca(Biblioteca biblioteca) {
		this.bibliotecas.add(biblioteca);
		return this;
	}

	public Estudiante removeBiblioteca(Biblioteca biblioteca) {
		this.bibliotecas.remove(biblioteca);
		return this;
	}

	public void setBibliotecas(Set<Biblioteca> bibliotecas) {
		this.bibliotecas = bibliotecas;
	}

	public Set<Aviso> getAvisos() {
		return avisos;
	}

	public Estudiante avisos(Set<Aviso> avisos) {
		this.avisos = avisos;
		return this;
	}

	public Estudiante addAviso(Aviso aviso) {
		this.avisos.add(aviso);
		aviso.setEstudiante(this);
		return this;
	}

	public Estudiante removeAviso(Aviso aviso) {
		this.avisos.remove(aviso);
		aviso.setEstudiante(null);
		return this;
	}

	public void setAvisos(Set<Aviso> avisos) {
		this.avisos = avisos;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here, do not remove

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Estudiante estudiante = (Estudiante) o;
		if (estudiante.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), estudiante.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Estudiante [id=" + id + ", user=" + user + ", idBibliotecaEsta=" + idBibliotecaEsta + ", codigoQR="
				+ codigoQR + ", pausa=" + pausa + ", fechaModificacion=" + fechaModificacion + ", reportes=" + reportes
				+ ", bibliotecas=" + bibliotecas + ", avisos=" + avisos + "]";
	}

}
