package com.leverx.pets.service.impl;

import com.leverx.pets.model.Pet;
import com.leverx.pets.repository.PetRepository;
import com.leverx.pets.service.PetService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PetServiceImpl implements PetService {

    @Inject
    private PetRepository petRepository;

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
