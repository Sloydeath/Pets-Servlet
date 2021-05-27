package com.leverx.pets.config;

import com.leverx.pets.controller.servlet.PersonServlet;
import com.leverx.pets.repository.PersonRepository;
import com.leverx.pets.repository.PetRepository;
import com.leverx.pets.repository.impl.PersonRepositoryImpl;
import com.leverx.pets.repository.impl.PetRepositoryImpl;
import com.leverx.pets.service.PersonService;
import com.leverx.pets.service.PetService;
import com.leverx.pets.service.impl.PersonServiceImpl;
import com.leverx.pets.service.impl.PetServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

    private static final Logger log = Logger.getLogger(ContextListener.class);
    private PersonRepository personRepository;
    private PetRepository petRepository;

    private PersonService personService;
    private PetService petService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        initRepositories();
        initServices();

        ServletContext servletContext = sce.getServletContext();
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
    }

    private void initRepositories() {
        personRepository = new PersonRepositoryImpl();
        petRepository = new PetRepositoryImpl();
    }

    private void initServices() {
        personService = new PersonServiceImpl(personRepository);
        petService = new PetServiceImpl(petRepository);
    }
}
