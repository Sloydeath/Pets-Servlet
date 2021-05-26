package com.leverx.pets.service.impl;

import com.leverx.pets.model.Person;
import com.leverx.pets.repository.PersonRepository;
import com.leverx.pets.service.PersonService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PersonServiceImpl implements PersonService {

    @Inject
    private PersonRepository personRepository;

    @Override
    public void createPerson(Person person) {
        personRepository.create(person);
    }

    @Override
    public List<Person> getAllPeople() {
        return personRepository.getAll();
    }

    @Override
    public Person getPersonById(Long id) {

        return personRepository.getById(id);
    }

    @Override
    public void deletePersonById(Long id) {
        personRepository.delete(id);
    }

    @Override
    public void updatePerson(Person person) {
        personRepository.update(person);
    }
}
