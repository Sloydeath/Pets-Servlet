package com.leverx.pets.config;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryConfig {

    private static final Logger log = Logger.getLogger(EntityManagerFactoryConfig.class);

    private static EntityManagerFactory entityManagerFactory() {
        log.info("Creation of entityManagerFactory");
        return Persistence.createEntityManagerFactory("hibernate-pets");
    }

    public static EntityManager entityManager() {
        EntityManagerFactory entityManagerFactory = entityManagerFactory();
        return entityManagerFactory.createEntityManager();
    }
}
