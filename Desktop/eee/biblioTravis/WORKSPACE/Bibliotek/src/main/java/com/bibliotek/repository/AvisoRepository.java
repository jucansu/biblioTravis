package com.bibliotek.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.bibliotek.domain.Aviso;


/**
 * Spring Data  repository for the Aviso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvisoRepository extends JpaRepository<Aviso, Long> {

}
