package com.leverx.pets.config;

import com.leverx.pets.model.Cat;
import com.leverx.pets.model.Dog;
import com.leverx.pets.model.Person;
import com.leverx.pets.model.Pet;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {

    private static final Logger log = Logger.getLogger(HibernateSessionFactoryUtil.class);
    private static SessionFactory sessionFactory = null;

    static {
        try {
            loadSessionFactory();
        } catch(Exception e){
            log.error("Exception while initializing HibernateSessionFactoryUtil: " + e);
        }
    }

    private static void loadSessionFactory(){
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Person.class);
        configuration.addAnnotatedClass(Pet.class);
        configuration.addAnnotatedClass(Dog.class);
        configuration.addAnnotatedClass(Cat.class);

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    public static Session getSession() throws HibernateException {

        Session session = null;
        try {
            session = sessionFactory.openSession();
        } catch (Throwable t) {
            log.error("Exception while getting session: " + t);
        }
        if (session == null) {
            log.warn("session is discovered null");
        }

        return session;
    }
}
