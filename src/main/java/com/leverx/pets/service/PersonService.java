package com.leverx.pets.service;

import com.leverx.pets.model.Person;

import java.util.List;

public interface PersonService {
    void createPerson(Person person);
    List<Person> getAllPeople();
    Person getPersonById(Long id);
    void deletePersonById(Long id);
    void updatePerson(Person person);
}
