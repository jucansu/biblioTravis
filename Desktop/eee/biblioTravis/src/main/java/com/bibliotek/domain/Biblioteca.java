package com.bibliotek.domain;


import com.bibliotek.domain.enumeration.TipoZona;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "biblioteca")
public class Biblioteca implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "valoracion")
    private Double valoracion;

    public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	@Column(name = "plazas_totales")
    private Integer plazasTotales;

    @Column(name = "plazas_ocupadas")
    private Integer plazasOcupadas;

    @Enumerated(EnumType.STRING)
    @Column(name = "zona")
    private TipoZona zona;

    @Column(name = "num_enchufes")
    private Integer numEnchufes;

    @Column(name = "salas")
    private Integer salas;
    @Column(name = "foto")
    private  String foto;
    
    //Nuevos atributos
    
    @Column(name = "horario")
    private  String horario;
    
    @Column(name = "direccion")
    private  String direccion;
    
    @Column(name = "foto_detalle")
    private  String fotoDetalle;
    
    @Column(name = "url_direccion")
    private  String urlDireccion;
    
    @Column(name = "num_votos")
    private  Integer numVotos;

	

	public Integer getNumVotos() {
		return numVotos;
	}

	public void setNumVotos(Integer numVotos) {
		this.numVotos = numVotos;
	}

	/**
     * Biblioteca
     */
    @ApiModelProperty(value = "Biblioteca")

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @Column(name="estudiante_id")
    private Set<Estudiante> estudiante= new HashSet<>();
    
  

	@OneToMany(mappedBy = "biblioteca",cascade = CascadeType.ALL)
    private Set<Correccion> correccions = new HashSet<>();
    @OneToMany(mappedBy = "biblioteca",cascade = CascadeType.ALL)
    private Set<Reporte> reportes = new HashSet<>();
    @OneToMany(mappedBy = "biblioteca",cascade = CascadeType.ALL)
    private Set<Bibliotecario> bibliotecarios = new HashSet<>();
    @OneToMany(mappedBy = "biblioteca",cascade = CascadeType.ALL)
    private Set<NotificacionInfo> notificacionInfos = new HashSet<>();
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

    public Biblioteca nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Biblioteca descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public Set<Estudiante> getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Set<Estudiante> estudiante) {
		this.estudiante = estudiante;
	}

	public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getValoracion() {
        return valoracion;
    }

    public Biblioteca valoracion(Double valoracion) {
        this.valoracion = valoracion;
        return this;
    }
    public Biblioteca plazasOcupadas(Integer plazasOcupadas) {
        this.plazasOcupadas = plazasOcupadas;
        return this;
    }
    public void setValoracion(Double valoracion) {
        this.valoracion = valoracion;
    }

    public Integer getPlazasTotales() {
        return plazasTotales;
    }

    public Biblioteca plazasTotales(Integer plazasTotales) {
        this.plazasTotales = plazasTotales;
        return this;
    }

    public void setPlazasTotales(Integer plazasTotales) {
        this.plazasTotales = plazasTotales;
    }

    public TipoZona getZona() {
        return zona;
    }

    public Biblioteca zona(TipoZona zona) {
        this.zona = zona;
        return this;
    }

    public void setZona(TipoZona zona) {
        this.zona = zona;
    }

    public Integer getNumEnchufes() {
        return numEnchufes;
    }

    public Biblioteca numEnchufes(Integer numEnchufes) {
        this.numEnchufes = numEnchufes;
        return this;
    }

    public void setNumEnchufes(Integer numEnchufes) {
        this.numEnchufes = numEnchufes;
    }

    public Integer getSalas() {
        return salas;
    }

    public Biblioteca salas(Integer salas) {
        this.salas = salas;
        return this;
    }

    public void setSalas(Integer salas) {
        this.salas = salas;
    }

   

    public Set<Correccion> getCorreccions() {
        return correccions;
    }

    public Biblioteca correccions(Set<Correccion> correccions) {
        this.correccions = correccions;
        return this;
    }

    public Biblioteca addCorreccion(Correccion correccion) {
        this.correccions.add(correccion);
        correccion.setBiblioteca(this);
        return this;
    }

    public Biblioteca removeCorreccion(Correccion correccion) {
        this.correccions.remove(correccion);
        correccion.setBiblioteca(null);
        return this;
    }

    public void setCorreccions(Set<Correccion> correccions) {
        this.correccions = correccions;
    }

    public Set<Reporte> getReportes() {
        return reportes;
    }

    public Biblioteca reportes(Set<Reporte> reportes) {
        this.reportes = reportes;
        return this;
    }

    public Biblioteca addReporte(Reporte reporte) {
        this.reportes.add(reporte);
        reporte.setBiblioteca(this);
        return this;
    }

    public Biblioteca removeReporte(Reporte reporte) {
        this.reportes.remove(reporte);
        reporte.setBiblioteca(null);
        return this;
    }

    public void setReportes(Set<Reporte> reportes) {
        this.reportes = reportes;
    }

    public Set<Bibliotecario> getBibliotecarios() {
        return bibliotecarios;
    }

    public Biblioteca bibliotecarios(Set<Bibliotecario> bibliotecarios) {
        this.bibliotecarios = bibliotecarios;
        return this;
    }

    public Biblioteca addBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecarios.add(bibliotecario);
        bibliotecario.setBiblioteca(this);
        return this;
    }

    public Biblioteca removeBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecarios.remove(bibliotecario);
        bibliotecario.setBiblioteca(null);
        return this;
    }

    public void setBibliotecarios(Set<Bibliotecario> bibliotecarios) {
        this.bibliotecarios = bibliotecarios;
    }

    public Set<NotificacionInfo> getNotificacionInfos() {
        return notificacionInfos;
    }

    public Biblioteca notificacionInfos(Set<NotificacionInfo> notificacionInfos) {
        this.notificacionInfos = notificacionInfos;
        return this;
    }

    public Biblioteca addNotificacionInfo(NotificacionInfo notificacionInfo) {
        this.notificacionInfos.add(notificacionInfo);
        notificacionInfo.setBiblioteca(this);
        return this;
    }

    public Biblioteca removeNotificacionInfo(NotificacionInfo notificacionInfo) {
        this.notificacionInfos.remove(notificacionInfo);
        notificacionInfo.setBiblioteca(null);
        return this;
    }

    public void setNotificacionInfos(Set<NotificacionInfo> notificacionInfos) {
        this.notificacionInfos = notificacionInfos;
    }
    
    

    public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getFotoDetalle() {
		return fotoDetalle;
	}

	public void setFotoDetalle(String fotoDetalle) {
		this.fotoDetalle = fotoDetalle;
	}

	public String getUrlDireccion() {
		return urlDireccion;
	}

	public void setUrlDireccion(String urlDireccion) {
		this.urlDireccion = urlDireccion;
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
        Biblioteca biblioteca = (Biblioteca) o;
        if (biblioteca.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), biblioteca.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	public Integer getPlazasOcupadas() {
		return plazasOcupadas;
	}

	public void setPlazasOcupadas(Integer plazasOcupadas) {
		this.plazasOcupadas = plazasOcupadas;
	}

	@Override
	public String toString() {
		return "Biblioteca [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", valoracion="
				+ valoracion + ", plazasTotales=" + plazasTotales + ", plazasOcupadas=" + plazasOcupadas + ", zona="
				+ zona + ", numEnchufes=" + numEnchufes + ", salas=" + salas + ", correccions=" + correccions
				+ ", reportes=" + reportes + ", bibliotecarios=" + bibliotecarios + ", notificacionInfos="
				+ notificacionInfos + "]";
	}

}
