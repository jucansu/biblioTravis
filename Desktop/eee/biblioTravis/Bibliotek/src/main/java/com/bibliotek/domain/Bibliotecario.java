package com.bibliotek.domain;


import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

/**
 * A Bibliotecario.
 */
@Entity
@Table(name = "bibliotecario")
public class Bibliotecario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @OneToOne(cascade = CascadeType.ALL)
	@Nullable
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
     * Bibliotecario
     */
    @ApiModelProperty(value = "Bibliotecario")
    @OneToMany(mappedBy = "bibliotecario",cascade = CascadeType.ALL)
    private Set<Correccion> correccions = new HashSet<>();
    @OneToMany(mappedBy = "bibliotecario",cascade = CascadeType.ALL)
    private Set<NotificacionInfo> notificacionInfos = new HashSet<>();
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("bibliotecarios")
    private Biblioteca biblioteca;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Correccion> getCorreccions() {
        return correccions;
    }

    public Bibliotecario correccions(Set<Correccion> correccions) {
        this.correccions = correccions;
        return this;
    }

    public Bibliotecario addCorreccion(Correccion correccion) {
        this.correccions.add(correccion);
        correccion.setBibliotecario(this);
        return this;
    }

    public Bibliotecario removeCorreccion(Correccion correccion) {
        this.correccions.remove(correccion);
        correccion.setBibliotecario(null);
        return this;
    }

    public void setCorreccions(Set<Correccion> correccions) {
        this.correccions = correccions;
    }

    public Set<NotificacionInfo> getNotificacionInfos() {
        return notificacionInfos;
    }

    public Bibliotecario notificacionInfos(Set<NotificacionInfo> notificacionInfos) {
        this.notificacionInfos = notificacionInfos;
        return this;
    }

    public Bibliotecario addNotificacionInfo(NotificacionInfo notificacionInfo) {
        this.notificacionInfos.add(notificacionInfo);
        notificacionInfo.setBibliotecario(this);
        return this;
    }

    public Bibliotecario removeNotificacionInfo(NotificacionInfo notificacionInfo) {
        this.notificacionInfos.remove(notificacionInfo);
        notificacionInfo.setBibliotecario(null);
        return this;
    }

    public void setNotificacionInfos(Set<NotificacionInfo> notificacionInfos) {
        this.notificacionInfos = notificacionInfos;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public Bibliotecario biblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        return this;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
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
        Bibliotecario bibliotecario = (Bibliotecario) o;
        if (bibliotecario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bibliotecario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bibliotecario{" +
            "id=" + getId() +
            "}";
    }
}
