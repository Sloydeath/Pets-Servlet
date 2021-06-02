package com.leverx.pets.repository;

import com.leverx.pets.model.pet.Pet;

import java.util.List;

public interface PetRepository {
    void create(Pet pet);
    List<Pet> getAll();
    Pet getById(Long id);
    boolean delete(Long id);
    void update(Pet pet);
}
