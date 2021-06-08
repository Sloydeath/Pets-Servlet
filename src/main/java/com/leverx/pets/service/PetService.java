package com.leverx.pets.service;

import com.leverx.pets.dto.PetDto;
import com.leverx.pets.dto.UpdatePetDto;
import com.leverx.pets.model.pet.Pet;

import java.util.List;

public interface PetService {
    Pet createPet(PetDto petDto);
    List<Pet> getAllPets();
    Pet getPetById(Long id);
    void deletePetById(Long id);
    Pet updatePet(UpdatePetDto petDto, Long id);
}
