package com.bibliotek.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bibliotek.domain.Biblioteca;


/**
 * Spring Data  repository for the Biblioteca entity.
 */
@Repository
public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long> {
	@Transactional
	@Modifying
	@Query("update Biblioteca b set b.plazasOcupadas = ?1 where b.id = ?2")
	void updateBiblioteca(int plazasOcupadas,Long id);
	
	@Transactional
	@Modifying
	  @Query("select a from Biblioteca a where a.id = ?1")
	  Optional<Biblioteca> findByIdBiblioteca(Long id);
	
	@Transactional
	@Modifying
	  @Query("select a from Biblioteca a where a.id = ?1")
	  Optional<List<Biblioteca>> findByIdEstudiante(Long id);
	
	@Transactional
	@Modifying
	@Query("update Biblioteca b set b.valoracion = ?1 where b.id = ?2")
	void updateBibliotecaSuma(Double valoracion, Long id);

}
