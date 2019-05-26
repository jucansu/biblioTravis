package com.bibliotek.service.impl;

import com.bibliotek.domain.Admin;
import com.bibliotek.repository.AdminRepository;
import com.bibliotek.service.AdminService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Admin.
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Save a admin.
     *
     * @param admin the entity to save
     * @return the persisted entity
     */
    @Override
    public Admin save(Admin admin) {
        log.debug("Request to save Admin : {}", admin);
        return adminRepository.save(admin);
    }

    /**
     * Get all the admins.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Admin> findAll() {
        log.debug("Request to get all Admins");
        return adminRepository.findAll();
    }


    /**
     * Get one admin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Admin> findOne(Long id) {
        log.debug("Request to get Admin : {}", id);
        return adminRepository.findById(id);
    }

    /**
     * Delete the admin by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Admin : {}", id);
        adminRepository.deleteById(id);
    }
}
