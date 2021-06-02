package com.leverx.pets.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.pets.dto.PetDto;
import com.leverx.pets.model.Person;
import com.leverx.pets.model.pet.Pet;
import com.leverx.pets.repository.PersonRepository;
import com.leverx.pets.repository.PetRepository;
import com.leverx.pets.service.PetService;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.BufferedReader;
import java.util.List;

import static com.leverx.pets.factory.PetFactory.getPet;
import static com.leverx.pets.util.StringConstantsUtil.EMPTY;
import static com.leverx.pets.util.StringConstantsUtil.FALSE;
import static com.leverx.pets.util.StringConstantsUtil.TRUE;

public class PetServiceImpl implements PetService {

    private static final Logger log = Logger.getLogger(PetServiceImpl.class);
    private final PetRepository petRepository;
    private final PersonRepository personRepository;
    private final EntityManager entityManager;
    private final ObjectMapper objectMapper;

    public PetServiceImpl(PetRepository petRepository, PersonRepository personRepository, EntityManager entityManager, ObjectMapper objectMapper) {
        this.petRepository = petRepository;
        this.personRepository = personRepository;
        this.entityManager = entityManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean createPet(BufferedReader petJson, Long personId) {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            PetDto petDto = objectMapper.readValue(petJson, PetDto.class);
            Pet pet = getPet(petDto.getPetType());
            Person person = personRepository.getById(personId);
            if (person != null && pet != null) {
                pet.setName(petDto.getName());
                pet.setPerson(person);
                petRepository.create(pet);
                et.commit();
                return TRUE;
            }
            else {
                throw new Exception("Person doesn't exist");
            }
        } catch (Exception ex) {
            log.error("Exception while execution of createPet: " + ex);
            et.rollback();
            return FALSE;
        }
    }

    @Override
    public String getAllPets() {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            List<Pet> pets = petRepository.getAll();
            et.commit();
            if (pets != null && !pets.isEmpty()) {
                return objectMapper.writeValueAsString(pets);
            }
            else {
                return EMPTY;
            }
        } catch (Exception ex) {
            log.error("Exception while execution of getAllPets: " + ex);
            et.rollback();
            return EMPTY;
        }
    }

    @Override
    public String getPetById(Long id) {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            Pet pet = petRepository.getById(id);
            et.commit();
            if (pet != null) {
                return objectMapper.writeValueAsString(pet);
            }
            else {
                return EMPTY;
            }
        } catch (Exception ex) {
            log.error("Exception while execution of getPetById: " + ex);
            et.rollback();
            return EMPTY;
        }
    }

    @Override
    public boolean deletePet(Long id) {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            boolean isDelete = petRepository.delete(id);
            et.commit();
            return isDelete;
        } catch (Exception ex) {
            log.error("Exception while execution of deletePet: " + ex);
            et.rollback();
            return FALSE;
        }
    }

    @Override
    public boolean updatePet(BufferedReader petJson, Long id) {
        EntityTransaction et = entityManager.getTransaction();
        try {
            et.begin();
            Pet pet = petRepository.getById(id);
            if (pet != null) {
                PetDto petDto = objectMapper.readValue(petJson, PetDto.class);
                pet.setName(petDto.getName());
                petRepository.update(pet);
                et.commit();
                return TRUE;
            }
            else {
                throw new Exception("Pet doesn't exist");
            }
        } catch (Exception ex) {
            log.error("Exception while execution of updatePet: " + ex);
            et.rollback();
            return FALSE;
        }
    }
}
