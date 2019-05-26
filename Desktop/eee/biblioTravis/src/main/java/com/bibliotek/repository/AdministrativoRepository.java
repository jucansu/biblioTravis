package com.bibliotek.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.bibliotek.domain.Administrativo;


/**
 * Spring Data  repository for the Administrativo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdministrativoRepository extends JpaRepository<Administrativo, Long> {

}
