package com.leverx.pets.repository.impl;

import com.leverx.pets.model.Person;
import com.leverx.pets.repository.PersonRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

public class PersonRepositoryImpl implements PersonRepository {

    private final EntityManager entityManager;

    public PersonRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Person create(Person person) {
        entityManager.persist(person);
        return person;
    }

    @Override
    public List<Person> getAll() {
        return entityManager.createQuery("FROM Person", Person.class).getResultList();
    }

    @Override
    public Optional<Person> getById(Long id) {
        return Optional.ofNullable(entityManager.find(Person.class, id));
    }

    @Override
    public void delete(Long id) {
        Person person = entityManager.find(Person.class, id);
        if (nonNull(person)) {
            entityManager.remove(person);
        }
        else {
            throw new IllegalArgumentException(String.format("Person with ID %d doesn't exist or ID is incorrect", id));
        }
    }

    @Override
    public Person update(Person person) {
        Person currentPerson = entityManager.find(Person.class, person.getId());
        if (nonNull(currentPerson)) {
            currentPerson.setName(person.getName());
            currentPerson.setPets(person.getPets());
            return currentPerson;
        }
        throw new IllegalArgumentException(String.format("Person with ID %d doesn't exist or ID is incorrect", person.getId()));
    }
}
