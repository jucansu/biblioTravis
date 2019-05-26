package com.bibliotek.service.impl;

import com.bibliotek.domain.Actor;
import com.bibliotek.repository.ActorRepository;
import com.bibliotek.service.ActorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Actor.
 */
@Service
@Transactional
public class ActorServiceImpl implements ActorService {

    private final Logger log = LoggerFactory.getLogger(ActorServiceImpl.class);

    private final ActorRepository actorRepository;

    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    /**
     * Save a actor.
     *
     * @param actor the entity to save
     * @return the persisted entity
     */
    @Override
    public Actor save(Actor actor) {
        log.debug("Request to save Actor : {}", actor);
        return actorRepository.save(actor);
    }

    /**
     * Get all the actors.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Actor> findAll() {
        log.debug("Request to get all Actors");
        return actorRepository.findAll();
    }


    /**
     * Get one actor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Actor> findOne(Long id) {
        log.debug("Request to get Actor : {}", id);
        return actorRepository.findById(id);
    }

    /**
     * Delete the actor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Actor : {}", id);
        actorRepository.deleteById(id);
    }
}
