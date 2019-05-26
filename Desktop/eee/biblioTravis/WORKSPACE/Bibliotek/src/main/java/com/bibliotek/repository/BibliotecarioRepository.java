package com.bibliotek.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.bibliotek.domain.Bibliotecario;


/**
 * Spring Data  repository for the Bibliotecario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {

}
