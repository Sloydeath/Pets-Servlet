package com.leverx.pets.repository.impl;

import com.leverx.pets.model.pet.Pet;
import com.leverx.pets.repository.PetRepository;

import javax.persistence.EntityManager;
import java.util.List;

import static java.util.Objects.nonNull;

public class PetRepositoryImpl implements PetRepository {

    private final EntityManager entityManager;

    public PetRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Pet pet) {
        entityManager.persist(pet);
    }

    @Override
    public List<Pet> getAll() {
       return entityManager.createQuery("FROM Pet", Pet.class).getResultList();
    }

    @Override
    public Pet getById(Long id) {
        return entityManager.find(Pet.class, id);
    }

    @Override
    public boolean delete(Long id) {
        Pet pet = getById(id);
        if (nonNull(pet)) {
            entityManager.remove(pet);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void update(Pet pet) {
        Pet currentPet = getById(pet.getId());
        currentPet.setName(pet.getName());
        currentPet.setPerson(pet.getPerson());
    }
}
