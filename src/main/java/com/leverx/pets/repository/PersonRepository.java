package com.leverx.pets.repository;

import com.leverx.pets.model.Person;

import java.util.List;

public interface PersonRepository {
    void create(Person person);
    List<Person> getAll();
    Person getById(Long id);
    boolean delete(Long id);
    void update(Person person);
}
