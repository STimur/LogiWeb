package com.tsystems.javaschool.timber.logiweb.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.List;

public interface GenericDao<T> {
    void persist(T entity);

    void update(T entity);

    T find(int id);

    void delete(int id);

    List<T> findAll();
}