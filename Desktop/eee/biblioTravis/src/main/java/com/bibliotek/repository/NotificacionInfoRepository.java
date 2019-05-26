package com.bibliotek.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.bibliotek.domain.NotificacionInfo;


/**
 * Spring Data  repository for the NotificacionInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificacionInfoRepository extends JpaRepository<NotificacionInfo, Long> {

}
