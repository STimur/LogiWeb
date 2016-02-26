package com.tsystems.javaschool.timber.logiweb.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.entity.Cargo;

/**
 * Created by tims on 2/18/2016.
 */
public class CargoDaoJpa extends GenericDaoJpa<Cargo> {

    public CargoDaoJpa(Class<Cargo> entityClass) {
        super(entityClass);
    }

    @Override
    public void persist(Cargo cargo) {
        openEntityManagerWithTransaction();
        if (getEntityManager().find(Cargo.class, cargo.getId()) != null)
            getEntityManager().merge(cargo);
        else
            getEntityManager().persist(cargo);
        closeEntityManagerWithTransaction();
    }
}
