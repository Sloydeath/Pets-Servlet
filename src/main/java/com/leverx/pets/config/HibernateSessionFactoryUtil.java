package com.leverx.pets.config;

import com.leverx.pets.model.Cat;
import com.leverx.pets.model.Dog;
import com.leverx.pets.model.Person;
import com.leverx.pets.model.Pet;
import com.leverx.pets.model.PetType;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
        Map<String, String> settings = new HashMap<>();
        try (InputStream input = new FileInputStream("D:\\Andrew\\Projects\\pets\\src\\main\\resources\\hibernate.properties")){
            Properties properties = new Properties();
            properties.load(input);
            settings.put("hibernate.connection.driver_class", properties.getProperty("hibernate.connection.driver_class"));
            settings.put("hibernate.connection.url", properties.getProperty("hibernate.connection.url"));
            settings.put("hibernate.connection.username", properties.getProperty("hibernate.connection.username"));
            settings.put("hibernate.connection.password", properties.getProperty("hibernate.connection.password"));
            settings.put("connection.pool_size", properties.getProperty("connection.pool_size"));
            settings.put("dialect", properties.getProperty("dialect"));
            settings.put("hibernate.enable_lazy_load_no_trans", properties.getProperty("hibernate.enable_lazy_load_no_trans"));
            settings.put("hbm2ddl.auto", properties.getProperty("hbm2ddl.auto"));

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(settings).build();

            MetadataSources metadataSources = new MetadataSources(serviceRegistry);

            metadataSources.addAnnotatedClass(Person.class);
            metadataSources.addAnnotatedClass(Pet.class);
            metadataSources.addAnnotatedClass(Cat.class);
            metadataSources.addAnnotatedClass(Dog.class);
            metadataSources.addAnnotatedClass(PetType.class);

            Metadata metadata = metadataSources.buildMetadata();

            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (IOException e) {
            log.error("Exception while opening  property file: " + e);
        }
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
