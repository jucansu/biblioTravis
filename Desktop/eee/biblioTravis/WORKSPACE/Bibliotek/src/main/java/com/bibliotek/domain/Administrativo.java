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
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import io.swagger.annotations.ApiModelProperty;

/**
 * A Administrativo.
 */
@Entity
@Table(name = "administrativo")
public class Administrativo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@Nullable
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	/**
	 * Administrativo
	 */
	@ApiModelProperty(value = "Administrativo")
	@OneToMany(mappedBy = "administrativo")
	private Set<NotificacionInfo> notificacionInfos = new HashSet<>();
	@OneToMany(mappedBy = "administrativo")
	private Set<SalaEstudio> salaEstudios = new HashSet<>();

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<NotificacionInfo> getNotificacionInfos() {
		return notificacionInfos;
	}

	public Administrativo notificacionInfos(Set<NotificacionInfo> notificacionInfos) {
		this.notificacionInfos = notificacionInfos;
		return this;
	}

	public Administrativo addNotificacionInfo(NotificacionInfo notificacionInfo) {
		this.notificacionInfos.add(notificacionInfo);
		notificacionInfo.setAdministrativo(this);
		return this;
	}

	public Administrativo removeNotificacionInfo(NotificacionInfo notificacionInfo) {
		this.notificacionInfos.remove(notificacionInfo);
		notificacionInfo.setAdministrativo(null);
		return this;
	}

	public void setNotificacionInfos(Set<NotificacionInfo> notificacionInfos) {
		this.notificacionInfos = notificacionInfos;
	}

	public Set<SalaEstudio> getSalaEstudios() {
		return salaEstudios;
	}

	public Administrativo salaEstudios(Set<SalaEstudio> salaEstudios) {
		this.salaEstudios = salaEstudios;
		return this;
	}

	public Administrativo addSalaEstudio(SalaEstudio salaEstudio) {
		this.salaEstudios.add(salaEstudio);
		salaEstudio.setAdministrativo(this);
		return this;
	}

	public Administrativo removeSalaEstudio(SalaEstudio salaEstudio) {
		this.salaEstudios.remove(salaEstudio);
		salaEstudio.setAdministrativo(null);
		return this;
	}

	public void setSalaEstudios(Set<SalaEstudio> salaEstudios) {
		this.salaEstudios = salaEstudios;
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
		Administrativo administrativo = (Administrativo) o;
		if (administrativo.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), administrativo.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Administrativo{" + "id=" + getId() + "}";
	}
}
