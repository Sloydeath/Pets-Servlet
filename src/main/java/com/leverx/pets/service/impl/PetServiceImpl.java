package com.leverx.pets.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.pets.dto.PetDto;
import com.leverx.pets.dto.UpdatePetDto;
import com.leverx.pets.exception.EntityNotFoundException;
import com.leverx.pets.model.Person;
import com.leverx.pets.model.pet.Pet;
import com.leverx.pets.repository.PersonRepository;
import com.leverx.pets.repository.PetRepository;
import com.leverx.pets.service.PetService;
import com.leverx.pets.util.ValidatorUtil;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.validation.Validator;
import java.util.List;

import static com.leverx.pets.factory.PetFactory.getPet;

public class PetServiceImpl implements PetService {

    private static final Logger log = Logger.getLogger(PetServiceImpl.class);
    private final PetRepository petRepository;
    private final PersonRepository personRepository;
    private final EntityManager entityManager;
    private final Validator validator;

    public PetServiceImpl(PetRepository petRepository, PersonRepository personRepository, EntityManager entityManager, ObjectMapper objectMapper, Validator validator) {
        this.petRepository = petRepository;
        this.personRepository = personRepository;
        this.entityManager = entityManager;
        this.validator = validator;
    }

    @Override
    public Pet createPet(PetDto petDto) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            ValidatorUtil.validateData(petDto, validator);
            Pet pet = getPet(petDto.getPetType());
            Person person = personRepository
                    .getById(petDto.getPersonId())
                    .orElseThrow(() -> new EntityNotFoundException("Person doesn't exist"));
            pet.setName(petDto.getName());
            pet.setPerson(person);
            petRepository.create(pet);
            transaction.commit();
            return pet;
        } catch (Exception ex) {
            log.error("Exception while execution of createPet: " + ex);
            transaction.rollback();
            throw new IllegalArgumentException("Pet isn't created: " + ex);
        }
    }

    @Override
    public List<Pet> getAllPets() {
        List<Pet> pets = petRepository.getAll();
        if (!pets.isEmpty()) {
            return pets;
        }
        else {
            log.error("Exception while execution of getAllPets: Pets aren't found");
            throw new EntityNotFoundException("Pets aren't found");
        }
    }

    @Override
    public Pet getPetById(Long id) {
        if (petRepository.getById(id).isPresent()) {
            return petRepository.getById(id).get();
        }
        else {
            log.error("Exception while execution of getPetById: Pet isn't found");
            throw new EntityNotFoundException("Pet isn't found");
        }
    }

    @Override
    public void deletePetById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            petRepository.delete(id);
            transaction.commit();
        } catch (Exception ex) {
            log.error("Exception while execution of deletePet: " + ex);
            transaction.rollback();
            throw new IllegalArgumentException("Pet isn't deleted:");
        }
    }

    @Override
    public Pet updatePet(UpdatePetDto petDto, Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            ValidatorUtil.validateData(petDto, validator);
            Pet pet = petRepository
                    .getById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Pet doesn't exist"));
            pet.setName(petDto.getName());
            petRepository.update(pet);
            transaction.commit();
            return pet;
        } catch (Exception ex) {
            log.error("Exception while execution of updatePet: " + ex);
            transaction.rollback();
            throw new IllegalArgumentException("Pet isn't updated");
        }
    }
}
