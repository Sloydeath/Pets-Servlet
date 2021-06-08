package com.leverx.pets.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.pets.repository.PersonRepository;
import com.leverx.pets.repository.PetRepository;
import com.leverx.pets.repository.impl.PersonRepositoryImpl;
import com.leverx.pets.repository.impl.PetRepositoryImpl;
import com.leverx.pets.service.PersonService;
import com.leverx.pets.service.PetService;
import com.leverx.pets.service.impl.PersonServiceImpl;
import com.leverx.pets.service.impl.PetServiceImpl;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.validation.Validation;
import javax.validation.Validator;

import static java.util.Objects.nonNull;

public class ApplicationContext implements ServletContextListener {

    private static final Logger log = Logger.getLogger(ApplicationContext.class);
    private EntityManager entityManager;
    private ObjectMapper objectMapper;
    private Validator validator;

    private PersonRepository personRepository;
    private PetRepository petRepository;

    private PersonService personService;
    private PetService petService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        initBeans();
        ServletContext servletContext = sce.getServletContext();

        //set Utils beans
        servletContext.setAttribute(ObjectMapper.class.getName(), objectMapper);
        servletContext.setAttribute(Validator.class.getName(), validator);

        //set Entity Manager
        servletContext.setAttribute(EntityManager.class.getName(), entityManager);

        //set repository beans
        servletContext.setAttribute(PersonRepository.class.getName(), personRepository);
        servletContext.setAttribute(PetRepository.class.getName(), petRepository);

        //set service beans
        servletContext.setAttribute(PersonService.class.getName(), personService);
        servletContext.setAttribute(PetService.class.getName(), petService);

        log.info("ServletContextListener initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("ServletContextListener destroyed");
        if (nonNull(entityManager)) {
            entityManager.close();
            entityManager.getEntityManagerFactory().close();
        }
    }

    private void initBeans() {
        initUtil();
        initEntityManager();
        initRepositories();
        initServices();
        log.info("Beans initialized");
    }

    private void initRepositories() {
        personRepository = new PersonRepositoryImpl(entityManager);
        petRepository = new PetRepositoryImpl(entityManager);
    }

    private void initServices() {
        personService = new PersonServiceImpl(personRepository, entityManager, objectMapper, validator);
        petService = new PetServiceImpl(petRepository, personRepository, entityManager, objectMapper, validator);
    }

    private void initEntityManager() {
        entityManager = EntityManagerFactoryConfig.entityManager();
    }

    private void initUtil() {
        objectMapper = new ObjectMapper();
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
