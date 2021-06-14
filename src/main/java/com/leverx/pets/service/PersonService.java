package com.leverx.pets.service;

import com.leverx.pets.dto.PersonDto;
import com.leverx.pets.model.Person;

import java.util.List;

public interface PersonService {
    Person createPerson(PersonDto personDto);
    List<Person> getAllPeople();
    Person getPersonById(Long id);
    void deletePersonById(Long id);
    Person updatePerson(PersonDto personDto, Long id);
}
