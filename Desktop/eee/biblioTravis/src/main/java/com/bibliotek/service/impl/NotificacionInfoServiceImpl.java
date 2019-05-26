package com.bibliotek.service.impl;

import com.bibliotek.domain.NotificacionInfo;
import com.bibliotek.repository.NotificacionInfoRepository;
import com.bibliotek.service.NotificacionInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing NotificacionInfo.
 */
@Service
@Transactional
public class NotificacionInfoServiceImpl implements NotificacionInfoService {

    private final Logger log = LoggerFactory.getLogger(NotificacionInfoServiceImpl.class);

    private final NotificacionInfoRepository notificacionInfoRepository;

    public NotificacionInfoServiceImpl(NotificacionInfoRepository notificacionInfoRepository) {
        this.notificacionInfoRepository = notificacionInfoRepository;
    }

    /**
     * Save a notificacionInfo.
     *
     * @param notificacionInfo the entity to save
     * @return the persisted entity
     */
    @Override
    public NotificacionInfo save(NotificacionInfo notificacionInfo) {
        log.debug("Request to save NotificacionInfo : {}", notificacionInfo);
        return notificacionInfoRepository.save(notificacionInfo);
    }

    /**
     * Get all the notificacionInfos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<NotificacionInfo> findAll() {
        log.debug("Request to get all NotificacionInfos");
        return notificacionInfoRepository.findAll();
    }


    /**
     * Get one notificacionInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NotificacionInfo> findOne(Long id) {
        log.debug("Request to get NotificacionInfo : {}", id);
        return notificacionInfoRepository.findById(id);
    }

    /**
     * Delete the notificacionInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NotificacionInfo : {}", id);
        notificacionInfoRepository.deleteById(id);
    }
}
