package com.bibliotek.service.dto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotek.domain.Biblioteca;

public interface BibliotecaDAO extends JpaRepository<Biblioteca, Integer>{
	public List<Biblioteca> findAllByOrderByLibreASC();
}
