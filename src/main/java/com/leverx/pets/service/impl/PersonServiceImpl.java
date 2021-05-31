package com.leverx.pets.service.impl;

import com.leverx.pets.model.Person;
import com.leverx.pets.repository.PersonRepository;
import com.leverx.pets.service.PersonService;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

public class PersonServiceImpl implements PersonService {

    private static final Logger log = Logger.getLogger(PersonServiceImpl.class);
    private final PersonRepository personRepository;
    private final EntityManager entityManager;

    public PersonServiceImpl(PersonRepository personRepository, EntityManager entityManager) {
        this.personRepository = personRepository;
        this.entityManager = entityManager;
    }

    @Override
    public void createPerson(Person person) {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            personRepository.create(person);
            et.commit();
        } catch (Exception ex) {
            log.error("Exception while execution of createPerson: " + ex);
            et.rollback();
        }
    }

    @Override
    public List<Person> getAllPeople() {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            List<Person> people = personRepository.getAll();
            et.commit();
            return people;
        } catch (Exception ex) {
            log.error("Exception while execution of getAllPeople: " + ex);
            et.rollback();
            return new ArrayList<>();
        }
    }

    @Override
    public Person getPersonById(Long id) {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            Person person = personRepository.getById(id);
            et.commit();
            return person;
        } catch (Exception ex) {
            log.error("Exception while execution of getPersonById: " + ex);
            et.rollback();
            return new Person();
        }
    }

    @Override
    public void deletePersonById(Long id) {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            personRepository.delete(id);
            et.commit();
        } catch (Exception ex) {
            log.error("Exception while execution of deletePersonById: " + ex);
            et.rollback();
        }
    }

    @Override
    public void updatePerson(Person person) {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            personRepository.update(person);
            et.commit();
        } catch (Exception ex) {
            log.error("Exception while execution of updatePerson: " + ex);
            et.rollback();
        }
    }
}
