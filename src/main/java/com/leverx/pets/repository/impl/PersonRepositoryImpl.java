package com.leverx.pets.repository.impl;

import com.leverx.pets.model.Person;
import com.leverx.pets.repository.PersonRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

import static com.leverx.pets.config.HibernateSessionFactoryUtil.getSession;

public class PersonRepositoryImpl implements PersonRepository {
    private static final Logger log = Logger.getLogger(PersonRepositoryImpl.class);

    @Override
    public void create(Person person) {
        Transaction transaction = null;

        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.save(person);
            transaction.commit();
        } catch (Exception ex) {
            log.error("Exception while executing create method in PersonRepositoryImpl: " + ex);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List getAll() {
        Transaction transaction = null;
        List people = null;
        final String hql = "FROM Person P ORDER BY P.id";
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(hql);
            people = query.list();
        } catch (Exception ex) {
            log.error("Exception while executing getAll method in PersonRepositoryImpl: " + ex);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return people;
    }

    @Override
    public Person getById(Long id) {
        try (Session session = getSession()) {
            return session.get(Person.class, id);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;

        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            Person person = session.get(Person.class, id);
            if (person != null) {
                session.delete(person);
            }
            transaction.commit();
        } catch (Exception ex) {
            log.error("Exception while executing delete method in PersonRepositoryImpl: " + ex);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void update(Person person) {
        Transaction transaction = null;

        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.update(person);
            transaction.commit();
        } catch (Exception ex) {
            log.error("Exception while executing update method in PersonRepositoryImpl: " + ex);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
