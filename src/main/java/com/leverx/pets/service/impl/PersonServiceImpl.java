package com.leverx.pets.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.pets.dto.PersonDto;
import com.leverx.pets.exception.EntityNotFoundException;
import com.leverx.pets.model.Person;
import com.leverx.pets.repository.PersonRepository;
import com.leverx.pets.service.PersonService;
import com.leverx.pets.util.ValidatorUtil;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.validation.Validator;
import java.util.List;

public class PersonServiceImpl implements PersonService {

    private static final Logger log = Logger.getLogger(PersonServiceImpl.class);
    private final PersonRepository personRepository;
    private final EntityManager entityManager;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    public PersonServiceImpl(PersonRepository personRepository, EntityManager entityManager, ObjectMapper objectMapper, Validator validator) {
        this.personRepository = personRepository;
        this.entityManager = entityManager;
        this.objectMapper = objectMapper;
        this.validator = validator;
    }

    @Override
    public Person createPerson(PersonDto personDto) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            ValidatorUtil.validate(personDto, validator);
            Person person = objectMapper.convertValue(personDto, Person.class);
            personRepository.create(person);
            transaction.commit();
            return person;
        } catch (Exception ex) {
            log.error("Exception while execution of createPerson: " + ex);
            transaction.rollback();
            throw new IllegalArgumentException("Person isn't saved: " + ex);
        }
    }

    @Override
    public List<Person> getAllPeople() {
        List<Person> people = personRepository.getAll();
        if (!people.isEmpty()) {
            return people;
        }
        else {
            log.error("Exception while execution of getAllPeople: People aren't found");
            throw new EntityNotFoundException("People aren't found");
        }
    }

    @Override
    public Person getPersonById(Long id) {
        if (personRepository.getById(id).isPresent()) {
            return personRepository.getById(id).get();
        }
        else {
            log.error("Exception while execution of getPersonById: Person isn't found");
            throw new EntityNotFoundException("Person isn't found");
        }
    }

    @Override
    public void deletePersonById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            personRepository.delete(id);
            transaction.commit();
        } catch (Exception ex) {
            log.error("Exception while execution of deletePersonById: " + ex);
            transaction.rollback();
            throw new IllegalArgumentException("Person isn't deleted");
        }
    }

    @Override
    public Person updatePerson(PersonDto personDto, Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            ValidatorUtil.validate(personDto, validator);
            Person person = personRepository
                    .getById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Person doesn't exist"));
            person.setName(personDto.getName());
            personRepository.update(person);
            transaction.commit();
            return person;
        } catch (Exception ex) {
            log.error("Exception while execution of updatePerson: " + ex);
            transaction.rollback();
            throw new IllegalArgumentException("Person isn't updated");
        }
    }
}
