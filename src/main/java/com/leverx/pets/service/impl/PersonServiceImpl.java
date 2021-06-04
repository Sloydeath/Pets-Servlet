package com.leverx.pets.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.pets.dto.PersonDto;
import com.leverx.pets.exception.EntityNotFoundException;
import com.leverx.pets.model.Person;
import com.leverx.pets.repository.PersonRepository;
import com.leverx.pets.service.PersonService;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.validation.Validator;
import java.io.BufferedReader;
import java.util.List;

import static com.leverx.pets.util.StringConstantsUtil.EMPTY;
import static com.leverx.pets.util.StringConstantsUtil.FALSE;
import static com.leverx.pets.util.StringConstantsUtil.TRUE;
import static com.leverx.pets.util.BeanValidator.validateBean;
import static java.util.Objects.nonNull;


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
    public boolean createPerson(BufferedReader personJson) {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            PersonDto personDto = objectMapper.readValue(personJson, PersonDto.class);
            validateBean(personDto, validator);
            personRepository.create(objectMapper.convertValue(personDto, Person.class));
            et.commit();
            return TRUE;
        } catch (Exception ex) {
            log.error("Exception while execution of createPerson: " + ex);
            et.rollback();
            return FALSE;
        }
    }

    @Override
    public String getAllPeople() {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            List<Person> people = personRepository.getAll();
            et.commit();
            return objectMapper.writeValueAsString(people);
        } catch (Exception ex) {
            log.error("Exception while execution of getAllPeople: " + ex);
            et.rollback();
            return EMPTY;
        }
    }

    @Override
    public String getPersonById(Long id) {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            Person person = personRepository.getById(id);
            et.commit();
            if (nonNull(person)) {
                return objectMapper.writeValueAsString(person);
            }
            else {
                return EMPTY;
            }
        } catch (Exception ex) {
            log.error("Exception while execution of getPersonById: " + ex);
            et.rollback();
            return EMPTY;
        }
    }

    @Override
    public boolean deletePersonById(Long id) {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            boolean isDelete = personRepository.delete(id);
            et.commit();
            return isDelete;
        } catch (Exception ex) {
            log.error("Exception while execution of deletePersonById: " + ex);
            et.rollback();
            return FALSE;
        }
    }

    @Override
    public boolean updatePerson(BufferedReader personJson, Long id) {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            Person person = personRepository.getById(id);
            if (nonNull(person)) {
                PersonDto personDto = objectMapper.readValue(personJson, PersonDto.class);
                validateBean(personDto, validator);
                person.setName(personDto.getName());
                personRepository.update(person);
                et.commit();
                return TRUE;
            }
            else {
                throw new EntityNotFoundException("Person doesn't exist");
            }
        } catch (Exception ex) {
            log.error("Exception while execution of updatePerson: " + ex);
            et.rollback();
            return FALSE;
        }
    }
}
