package com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Generic DAO interface for basic CRUD operations.
 *
 * @param <T> persistence object
 * @author Timur Salakhetdinov
 */
public interface GenericDao<T> {
    /**
     * Persist new object into database.
     *
     * @param entity persistence object
     * @return created persistence object
     */
    void persist(T entity);

    /**
     * Update persistence object.
     *
     * @param entity persistence object to update
     */
    void update(T entity);

    /**
     * Remove persistence object from database by its primarky key.
     *
     * @param id primary key of persistence object in database
     */
    void delete(int id);

    /**
     * Find persistence object by its primary key.
     *
     * @param id primary key of object
     * @return persistence object or null if not found
     */
    T find(int id);

    /**
     * Find all objects of that persistence class.
     *
     * @return list of objects or null
     */
    List<T> findAll();
}