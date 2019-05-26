package com.bibliotek.service;

import java.util.List;
import java.util.Optional;

import com.bibliotek.domain.Admin;

/**
 * Service Interface for managing Admin.
 */
public interface AdminService {

    /**
     * Save a admin.
     *
     * @param admin the entity to save
     * @return the persisted entity
     */
    Admin save(Admin admin);

    /**
     * Get all the admins.
     *
     * @return the list of entities
     */
    List<Admin> findAll();


    /**
     * Get the "id" admin.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Admin> findOne(Long id);

    /**
     * Delete the "id" admin.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
