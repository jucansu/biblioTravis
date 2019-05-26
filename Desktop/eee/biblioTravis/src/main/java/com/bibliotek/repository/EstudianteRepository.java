package com.bibliotek.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bibliotek.domain.Biblioteca;
import com.bibliotek.domain.Estudiante;
import com.bibliotek.domain.User;

/**
 * Spring Data repository for the Estudiante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
	Optional<Estudiante> findByUser(User user);

	@Transactional
	@Modifying
	@Query("update Estudiante e set e.idBibliotecaEsta = ?1, e.fechaModificacion = ?3 where e.id = ?2")
	void update(Long idBibliotecaEsta, Long id,Instant fecha);

	@Transactional

	@Modifying
	@Query("select a from Biblioteca a where a.id = ?1")
	Optional<Biblioteca> findByIdBiblioteca(Long id);

	@Transactional
	@Modifying
	@Query("update Estudiante b set b.pausa = ?1 where b.id = ?2")
	void updatePausa(Boolean pausa, Long id);
}
