package com.tsystems.javaschool.timber.logiweb.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.List;

public interface GeneralDaoInterface<T, Id extends Serializable> {
    final static EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Logiweb");

    public void persist(T entity);

    public void update(T entity);

    public T find(Id id);

    public void delete(Id id);

    public List<T> findAll();

    public void deleteAll();

}