package com.leverx.pets.repository.impl;

import com.leverx.pets.config.HibernateSessionFactoryUtil;
import com.leverx.pets.model.Pet;
import com.leverx.pets.repository.PetRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

import static com.leverx.pets.config.HibernateSessionFactoryUtil.*;

public class PetRepositoryImpl implements PetRepository {

    private static final Logger log = Logger.getLogger(PetRepositoryImpl.class);

    @Override
    public void create(Pet pet) {
        Transaction transaction = null;

        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.save(pet);
            transaction.commit();
        } catch (Exception ex) {
            log.error("Exception while executing create method in PetRepositoryImpl: " + ex);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List getAll() {
        Transaction transaction = null;
        List pets = null;
        final String hql = "FROM Pet P ORDER BY P.id";
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(hql);
            pets = query.list();
        } catch (Exception ex) {
            log.error("Exception while executing getAll method in PetRepositoryImpl: " + ex);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return pets;
    }

    @Override
    public Pet getById(Long id) {
        try (Session session = getSession()) {
            return session.get(Pet.class, id);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;

        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            Pet pet = session.get(Pet.class, id);
            if (pet != null) {
                session.delete(pet);
            }
            transaction.commit();
        } catch (Exception ex) {
            log.error("Exception while executing delete method in PetRepositoryImpl: " + ex);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void update(Pet pet) {
        Transaction transaction = null;

        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.update(pet);
            transaction.commit();
        } catch (Exception ex) {
            log.error("Exception while executing update method in PetRepositoryImpl: " + ex);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
