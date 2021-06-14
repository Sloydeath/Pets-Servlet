package com.leverx.pets.repository;

import com.leverx.pets.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    Person create(Person person);
    List<Person> getAll();
    Optional<Person> getById(Long id);
    void delete(Long id);
    Person update(Person person);
}
