/*
package com.leverx.pets.repository.impl;

import com.leverx.pets.model.pet.Cat;
import com.leverx.pets.model.Person;
import com.leverx.pets.model.pet.Pet;
import com.leverx.pets.repository.PersonRepository;
import com.leverx.pets.repository.PetRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PetRepositoryImplTest {
    private static final PetRepository petRepo = new PetRepositoryImpl();
    private static final PersonRepository personRepo = new PersonRepositoryImpl(entityManager);

    @Test
    public void WhenGetAllPetsThenAssertEqualsTrue() {
        List<Pet> actual = petRepo.getAll();
        int expected = 3;
        assertNotNull(actual);
        assertEquals(expected, actual.size());
    }

    @Test
    public void WhenDeletePetThenAssertNotEqualsTrue() {
        List<Pet> actual = petRepo.getAll();
        petRepo.delete(actual.get(0).getId());
        int expected = 2;
        assertNotEquals(expected, actual.size());
    }

    @Test
    public void WhenCreatePetThenAssertEqualsTrue() {
        Pet pet = new Cat();
        Person person = personRepo.getById(1L);
        pet.setName("Test-Name");
        pet.setPerson(person);
        petRepo.create(pet);
        List<Pet> actual = petRepo.getAll();
        int expected = 4;
        assertNotNull(actual);
        assertEquals(expected, actual.size());
    }

    @Test
    public void WhenUpdatePetThenAssertEqualsTrue() {
        Pet pet = petRepo.getById(1L);
        pet.setName("Updated name");
        petRepo.update(pet);
        String expected = "Updated name";
        assertEquals(expected, petRepo.getById(1L).getName());
    }
}*/
