package com.leverx.pets.repository.impl;

import com.leverx.pets.model.pet.Pet;
import com.leverx.pets.repository.PetRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

public class PetRepositoryImpl implements PetRepository {

    private final EntityManager entityManager;

    public PetRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Pet create(Pet pet) {
        entityManager.persist(pet);
        return pet;
    }

    @Override
    public List<Pet> getAll() {
       return entityManager.createQuery("FROM Pet", Pet.class).getResultList();
    }

    @Override
    public Optional<Pet> getById(Long id) {
        return Optional.ofNullable(entityManager.find(Pet.class, id));
    }

    @Override
    public void delete(Long id) {
        Pet pet = entityManager.find(Pet.class, id);
        if (nonNull(pet)) {
            entityManager.remove(pet);
        }
        else {
            throw new IllegalArgumentException(String.format("Pet with ID %d doesn't exist or ID is incorrect", id));
        }
    }

    @Override
    public Pet update(Pet pet) {
        Pet currentPet = entityManager.find(Pet.class, pet.getId());
        if (nonNull(currentPet)) {
            currentPet.setName(pet.getName());
            currentPet.setPerson(pet.getPerson());
            return currentPet;
        }
        throw new IllegalArgumentException(String.format("Pet with ID %d doesn't exist or ID is incorrect", pet.getId()));
    }
}
