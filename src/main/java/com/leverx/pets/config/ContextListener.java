package com.leverx.pets.config;

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

public class ContextListener implements ServletContextListener {

    private static final Logger log = Logger.getLogger(ContextListener.class);
    private PersonRepository personRepository;
    private PetRepository petRepository;
    private EntityManager entityManager;

    private PersonService personService;
    private PetService petService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        initEntityManager();
        initRepositories();
        initServices();

        ServletContext servletContext = sce.getServletContext();
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
        entityManager.close();
        entityManager.getEntityManagerFactory().close();
    }

    private void initRepositories() {
        personRepository = new PersonRepositoryImpl(entityManager);
        petRepository = new PetRepositoryImpl(entityManager);
    }

    private void initServices() {
        personService = new PersonServiceImpl(personRepository, entityManager);
        petService = new PetServiceImpl(petRepository, entityManager);
    }

    private void initEntityManager() {
        entityManager = EntityManagerFactoryUtil.entityManager();
    }
}
