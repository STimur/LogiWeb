package com.tsystems.javaschool.timber.logiweb.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.List;

public interface GeneralDaoInterface<T, Id extends Serializable> {
    EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Logiweb");

    void persist(T entity);

    void update(T entity);

    T find(Id id);

    void delete(Id id);

    List<T> findAll();

    void deleteAll();

}