package com.tsystems.javaschool.timber.logiweb.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.entity.Cargo;
import com.tsystems.javaschool.timber.logiweb.util.JpaUtil;

import javax.persistence.EntityManager;

/**
 * Created by tims on 2/18/2016.
 */
public class CargoDaoJpa extends GenericDaoJpa<Cargo> {

    public CargoDaoJpa(Class<Cargo> entityClass) {
        super(entityClass);
    }

    @Override
    public void persist(Cargo cargo) {
        EntityManager em = JpaUtil.getEntityManager();
        if (em.find(Cargo.class, cargo.getId()) != null)
            em.merge(cargo);
        else
            em.persist(cargo);
    }
}
