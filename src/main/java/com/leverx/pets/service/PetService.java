package com.leverx.pets.service;

import com.leverx.pets.model.pet.Pet;

import java.util.List;

public interface PetService {
    void createPet(Pet pet);
    List<Pet> getAllPets();
    Pet getPetById(Long id);
    void deletePet(Long id);
    void updatePet(Pet pet);
}
