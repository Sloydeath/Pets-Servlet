package com.leverx.pets.service;

import java.io.BufferedReader;

public interface PetService {
    boolean createPet(BufferedReader pet, Long personId);
    String getAllPets();
    String getPetById(Long id);
    boolean deletePet(Long id);
    boolean updatePet(BufferedReader pet, Long id);
}
