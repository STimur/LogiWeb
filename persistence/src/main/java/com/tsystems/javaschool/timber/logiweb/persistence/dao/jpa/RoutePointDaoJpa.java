package com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.RoutePoint;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.RoutePointDao;
import org.springframework.stereotype.Repository;

/**
 * Created by tims on 2/18/2016.
 */
@Repository
public class RoutePointDaoJpa extends GenericDaoJpa<RoutePoint> implements RoutePointDao {
}
