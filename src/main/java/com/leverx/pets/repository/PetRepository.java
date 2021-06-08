package com.leverx.pets.repository;

import com.leverx.pets.model.pet.Pet;

import java.util.List;
import java.util.Optional;

public interface PetRepository {
    Pet create(Pet pet);
    List<Pet> getAll();
    Optional<Pet> getById(Long id);
    void delete(Long id);
    Pet update(Pet pet);
}
