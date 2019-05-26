package com.bibliotek.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.bibliotek.domain.Correccion;


/**
 * Spring Data  repository for the Correccion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorreccionRepository extends JpaRepository<Correccion, Long> {

}
