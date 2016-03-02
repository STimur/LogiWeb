package com.tsystems.javaschool.timber.logiweb.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.dao.interfaces.DistanceDao;
import com.tsystems.javaschool.timber.logiweb.entity.Distance;

/**
 * Created by tims on 2/18/2016.
 */
public class DistanceDaoJpa extends GenericDaoJpa<Distance> implements DistanceDao {

    public DistanceDaoJpa(Class<Distance> entityClass) {
        super(entityClass);
    }
}
