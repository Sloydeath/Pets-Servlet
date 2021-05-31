/*
package com.leverx.pets.repository.impl;

import com.leverx.pets.model.Person;
import com.leverx.pets.repository.PersonRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PersonRepositoryImplTest {
    private static final PersonRepository personRepo = new PersonRepositoryImpl(entityManager);

    @Test
    public void WhenGetAllPeopleThenAssertEqualsTrue() {
        List<Person> actual = personRepo.getAll();
        int expected = 3;
        assertNotNull(actual);
        assertEquals(expected, actual.size());
    }

    @Test
    public void WhenDeletePersonThenAssertNotEqualsTrue() {
        List<Person> actual = personRepo.getAll();
        personRepo.delete(actual.get(0).getId());
        int expected = 2;
        assertNotEquals(expected, actual.size());
    }

    @Test
    public void WhenCreatePersonThenAssertEqualsTrue() {
        Person person = new Person();
        person.setName("Test-Name");
        personRepo.create(person);
        List<Person> actual = personRepo.getAll();
        int expected = 4;
        assertNotNull(actual);
        assertEquals(expected, actual.size());
    }

    @Test
    public void WhenUpdatePersonThenAssertEqualsTrue() {
        Person person = personRepo.getById(1L);
        person.setName("Updated name");
        personRepo.update(person);
        String expected = "Updated name";
        assertEquals(expected, personRepo.getById(1L).getName());
    }
}*/
