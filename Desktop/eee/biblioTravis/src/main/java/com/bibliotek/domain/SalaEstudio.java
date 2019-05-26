package com.bibliotek.domain;

import com.bibliotek.domain.enumeration.TipoZona;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SalaEstudio.
 */
@Entity
@Table(name = "sala_estudio")
public class SalaEstudio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "valoracion")
	private String valoracion;

	@Column(name = "plazas_totales")
	private Integer plazasTotales;

	@Enumerated(EnumType.STRING)
	@Column(name = "zona")
	private TipoZona zona;

	@Column(name = "num_enchufes")
	private Integer numEnchufes;

	@Column(name = "hablar")
	private Boolean hablar;

	@ManyToOne
	@JsonIgnoreProperties("salaEstudios")
	private Administrativo administrativo;

	/**
	 * SalaEstudio
	 */
	@ApiModelProperty(value = "SalaEstudio")
	@OneToMany(mappedBy = "salaEstudio")
	private Set<NotificacionInfo> notificacionInfos = new HashSet<>();
	@OneToMany(mappedBy = "salaEstudio")
	private Set<Reporte> reportes = new HashSet<>();

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public SalaEstudio nombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public SalaEstudio descripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getValoracion() {
		return valoracion;
	}

	public SalaEstudio valoracion(String valoracion) {
		this.valoracion = valoracion;
		return this;
	}

	public void setValoracion(String valoracion) {
		this.valoracion = valoracion;
	}

	public Integer getPlazasTotales() {
		return plazasTotales;
	}

	public SalaEstudio plazasTotales(Integer plazasTotales) {
		this.plazasTotales = plazasTotales;
		return this;
	}

	public void setPlazasTotales(Integer plazasTotales) {
		this.plazasTotales = plazasTotales;
	}

	public TipoZona getZona() {
		return zona;
	}

	public SalaEstudio zona(TipoZona zona) {
		this.zona = zona;
		return this;
	}

	public void setZona(TipoZona zona) {
		this.zona = zona;
	}

	public Integer getNumEnchufes() {
		return numEnchufes;
	}

	public SalaEstudio numEnchufes(Integer numEnchufes) {
		this.numEnchufes = numEnchufes;
		return this;
	}

	public void setNumEnchufes(Integer numEnchufes) {
		this.numEnchufes = numEnchufes;
	}

	public Boolean isHablar() {
		return hablar;
	}

	public SalaEstudio hablar(Boolean hablar) {
		this.hablar = hablar;
		return this;
	}

	public void setHablar(Boolean hablar) {
		this.hablar = hablar;
	}

	public Administrativo getAdministrativo() {
		return administrativo;
	}

	public SalaEstudio administrativo(Administrativo administrativo) {
		this.administrativo = administrativo;
		return this;
	}

	public void setAdministrativo(Administrativo administrativo) {
		this.administrativo = administrativo;
	}

	public Set<NotificacionInfo> getNotificacionInfos() {
		return notificacionInfos;
	}

	public SalaEstudio notificacionInfos(Set<NotificacionInfo> notificacionInfos) {
		this.notificacionInfos = notificacionInfos;
		return this;
	}

	public SalaEstudio addNotificacionInfo(NotificacionInfo notificacionInfo) {
		this.notificacionInfos.add(notificacionInfo);
		notificacionInfo.setSalaEstudio(this);
		return this;
	}

	public SalaEstudio removeNotificacionInfo(NotificacionInfo notificacionInfo) {
		this.notificacionInfos.remove(notificacionInfo);
		notificacionInfo.setSalaEstudio(null);
		return this;
	}

	public void setNotificacionInfos(Set<NotificacionInfo> notificacionInfos) {
		this.notificacionInfos = notificacionInfos;
	}

	public Set<Reporte> getReportes() {
		return reportes;
	}

	public SalaEstudio reportes(Set<Reporte> reportes) {
		this.reportes = reportes;
		return this;
	}

	public SalaEstudio addReporte(Reporte reporte) {
		this.reportes.add(reporte);
		reporte.setSalaEstudio(this);
		return this;
	}

	public SalaEstudio removeReporte(Reporte reporte) {
		this.reportes.remove(reporte);
		reporte.setSalaEstudio(null);
		return this;
	}

	public void setReportes(Set<Reporte> reportes) {
		this.reportes = reportes;
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
		SalaEstudio salaEstudio = (SalaEstudio) o;
		if (salaEstudio.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), salaEstudio.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "SalaEstudio{" + "id=" + getId() + ", nombre='" + getNombre() + "'" + ", descripcion='"
				+ getDescripcion() + "'" + ", valoracion='" + getValoracion() + "'" + ", plazasTotales="
				+ getPlazasTotales() + ", zona='" + getZona() + "'" + ", numEnchufes=" + getNumEnchufes() + ", hablar='"
				+ isHablar() + "'" + "}";
	}
}
