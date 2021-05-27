package com.leverx.pets.service.impl;

import com.leverx.pets.model.Pet;
import com.leverx.pets.repository.PetRepository;
import com.leverx.pets.service.PetService;

import java.util.List;

public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public void createPet(Pet pet) {
        petRepository.create(pet);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.getAll();
    }

    @Override
    public Pet getPetById(Long id) {
        return petRepository.getById(id);
    }

    @Override
    public void deletePet(Long id) {
        petRepository.delete(id);
    }

    @Override
    public void updatePet(Pet pet) {
        petRepository.update(pet);
    }
}
