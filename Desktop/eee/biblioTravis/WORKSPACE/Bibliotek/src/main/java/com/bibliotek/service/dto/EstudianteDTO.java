package com.bibliotek.service.dto;

import java.util.HashSet;
import java.util.Set;

import com.bibliotek.domain.Aviso;
import com.bibliotek.domain.Biblioteca;
import com.bibliotek.domain.Estudiante;
import com.bibliotek.domain.Reporte;
import com.bibliotek.domain.User;

public class EstudianteDTO {

	private Long id;
	private User user;
	private Long idBibliotecaEsta;
	
	private Set<Reporte> reportes = new HashSet<>();
	private Set<Biblioteca> bibliotecas = new HashSet<>();
	private String codigoQR;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public EstudianteDTO(Long id, User user, Long idBibliotecaEsta, Set<Reporte> reportes, Set<Biblioteca> bibliotecas,
			Set<Aviso> avisos) {
		super();
		this.id = id;
		this.user = user;
		this.idBibliotecaEsta = idBibliotecaEsta;
		this.reportes = reportes;
		this.bibliotecas = bibliotecas;
		this.avisos = avisos;
	}
	public EstudianteDTO(Estudiante estudiante) {
		super();
	}
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
	public Set<Reporte> getReportes() {
		return reportes;
	}
	public void setReportes(Set<Reporte> reportes) {
		this.reportes = reportes;
	}
	public Set<Biblioteca> getBibliotecas() {
		return bibliotecas;
	}
	public void setBibliotecas(Set<Biblioteca> bibliotecas) {
		this.bibliotecas = bibliotecas;
	}
	public Set<Aviso> getAvisos() {
		return avisos;
	}
	public void setAvisos(Set<Aviso> avisos) {
		this.avisos = avisos;
	}
	private Set<Aviso> avisos = new HashSet<>();

	@Override
	public String toString() {
		return "EstudianteDTO [id=" + id + ", user=" + user + ", idBibliotecaEsta=" + idBibliotecaEsta + ", reportes="
				+ reportes + ", bibliotecas=" + bibliotecas + ", avisos=" + avisos + "]";
	}
	public String getCodigoQR() {
		return codigoQR;
	}
	public void setCodigoQR(String codigoQR) {
		this.codigoQR = codigoQR;
	}
}
