package com.tsystems.javaschool.timber.logiweb.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.dao.DriverDao;
import com.tsystems.javaschool.timber.logiweb.dao.jpa.GenericDaoJpa;
import com.tsystems.javaschool.timber.logiweb.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.entity.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by tims on 2/15/2016.
 */
public class DriverDaoJpa extends GenericDaoJpa<Driver> implements DriverDao {

    public DriverDaoJpa(Class<Driver> entityClass) {
        super(entityClass);
    }

    @Override
    public List<Driver> getSuitableDriversForOrder(Order order) {
        openEntityManager();
        List<Driver> drivers = (List<Driver>) getEntityManager()
                .createNamedQuery("findSuitableDrivers")
                .setParameter("assignedTruckCity", order.getAssignedTruck().getCity())
                .setParameter("deliveryTime",order.getTimeEstimate())
                .getResultList();
        closeEntityManager();
        return drivers;
    }
}
