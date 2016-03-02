package com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.CargoDao;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Cargo;

/**
 * Created by tims on 2/18/2016.
 */
public class CargoDaoJpa extends GenericDaoJpa<Cargo> implements CargoDao {

    public CargoDaoJpa(Class<Cargo> entityClass) {
        super(entityClass);
    }
}
