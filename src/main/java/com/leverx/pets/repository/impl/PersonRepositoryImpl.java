package com.leverx.pets.repository.impl;

import com.leverx.pets.model.Person;
import com.leverx.pets.repository.PersonRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class PersonRepositoryImpl implements PersonRepository {
    private final EntityManager entityManager;

    public PersonRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Person person) {
        entityManager.persist(person);
    }

    @Override
    public List<Person> getAll() {
        return entityManager.createQuery("FROM Person", Person.class).getResultList();
    }

    @Override
    public Person getById(Long id) {
        return entityManager.find(Person.class, id);
    }

    @Override
    public boolean delete(Long id) {
        Person person = getById(id);
        if (person != null) {
            entityManager.remove(person);
            return true;
        }
        return false;
    }

    @Override
    public void update(Person person) {
        Person currentPerson = getById(person.getId());
        currentPerson.setName(person.getName());
        currentPerson.setPets(person.getPets());
    }
}
