package com.bibliotek.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.bibliotek.domain.SalaEstudio;


/**
 * Spring Data  repository for the SalaEstudio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalaEstudioRepository extends JpaRepository<SalaEstudio, Long> {

}
