package com.leverx.pets.repository;

import com.leverx.pets.model.Pet;

import java.util.List;

public interface PetRepository {
    void create(Pet pet);
    List<Pet> getAll();
    Pet getById(Long id);
    void delete(Long id);
    void update(Pet pet);
}
