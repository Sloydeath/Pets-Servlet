package com.leverx.pets.service.impl;

import com.leverx.pets.model.pet.Pet;
import com.leverx.pets.repository.PetRepository;
import com.leverx.pets.service.PetService;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

public class PetServiceImpl implements PetService {

    private static final Logger log = Logger.getLogger(PetServiceImpl.class);
    private final PetRepository petRepository;
    private final EntityManager entityManager;

    public PetServiceImpl(PetRepository petRepository, EntityManager entityManager) {
        this.petRepository = petRepository;
        this.entityManager = entityManager;
    }

    @Override
    public void createPet(Pet pet) {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            petRepository.create(pet);
            et.commit();
        } catch (Exception ex) {
            log.error("Exception while execution of createPet: " + ex);
            et.rollback();
        }
    }

    @Override
    public List<Pet> getAllPets() {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            List<Pet> pets = petRepository.getAll();
            et.commit();
            return pets;
        } catch (Exception ex) {
            log.error("Exception while execution of getAllPets: " + ex);
            et.rollback();
            return new ArrayList<>();
        }
    }

    @Override
    public Pet getPetById(Long id) {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            Pet pet = petRepository.getById(id);
            et.commit();
            return pet;
        } catch (Exception ex) {
            log.error("Exception while execution of getPetById: " + ex);
            et.rollback();
            return null;
        }
    }

    @Override
    public void deletePet(Long id) {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            petRepository.delete(id);
            et.commit();
        } catch (Exception ex) {
            log.error("Exception while execution of deletePet: " + ex);
            et.rollback();
        }
    }

    @Override
    public void updatePet(Pet pet) {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            petRepository.update(pet);
            et.commit();
        } catch (Exception ex) {
            log.error("Exception while execution of updatePet: " + ex);
            et.rollback();
        }
    }
}
