package com.bibliotek.service;

import java.util.List;
import java.util.Optional;

import com.bibliotek.domain.Actor;

/**
 * Service Interface for managing Actor.
 */
public interface ActorService {

    /**
     * Save a actor.
     *
     * @param actor the entity to save
     * @return the persisted entity
     */
    Actor save(Actor actor);

    /**
     * Get all the actors.
     *
     * @return the list of entities
     */
    List<Actor> findAll();


    /**
     * Get the "id" actor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Actor> findOne(Long id);

    /**
     * Delete the "id" actor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
