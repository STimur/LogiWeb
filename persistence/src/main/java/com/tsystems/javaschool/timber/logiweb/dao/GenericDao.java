package com.tsystems.javaschool.timber.logiweb.dao;

import java.util.List;

public interface GenericDao<T> {
    void persist(T entity);

    void update(T entity);

    T find(int id);

    void delete(int id);

    List<T> findAll();
}