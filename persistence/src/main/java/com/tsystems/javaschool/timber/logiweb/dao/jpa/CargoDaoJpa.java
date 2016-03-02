package com.tsystems.javaschool.timber.logiweb.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.dao.interfaces.CargoDao;
import com.tsystems.javaschool.timber.logiweb.entity.Cargo;
import com.tsystems.javaschool.timber.logiweb.dao.util.JpaUtil;

import javax.persistence.EntityManager;

/**
 * Created by tims on 2/18/2016.
 */
public class CargoDaoJpa extends GenericDaoJpa<Cargo> implements CargoDao {

    public CargoDaoJpa(Class<Cargo> entityClass) {
        super(entityClass);
    }
}
