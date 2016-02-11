package com.tsystems.javaschool.timber.logiweb.dao;

import java.io.Serializable;
import java.util.List;

public interface GeneralDaoInterface<T, Id extends Serializable> {
    public void persist(T entity);

    public void update(T entity);

    public T find(Id id);

    public void delete(Id id);

    public List<T> findAll();

    public void deleteAll();

}