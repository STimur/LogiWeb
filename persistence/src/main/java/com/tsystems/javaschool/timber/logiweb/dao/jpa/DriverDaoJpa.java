package com.tsystems.javaschool.timber.logiweb.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.dao.interfaces.DriverDao;
import com.tsystems.javaschool.timber.logiweb.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.entity.Order;
import com.tsystems.javaschool.timber.logiweb.dao.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by tims on 2/15/2016.
 */
public class DriverDaoJpa extends GenericDaoJpa<Driver> implements DriverDao {

    public DriverDaoJpa(Class<Driver> entityClass) {
        super(entityClass);
    }

    @Override
    public List<Driver> getSuitableDriversForOrder(Order order, int deliveryTimeThisMonth, int deliveryTimeNextMonth) {
        EntityManager em = JpaUtil.getEntityManager();
        List<Driver> drivers = (List<Driver>) em
                .createNamedQuery("findSuitableDrivers")
                .setParameter("assignedTruckCity", order.getAssignedTruck().getCity())
                .setParameter("deliveryTimeThisMonth", deliveryTimeThisMonth)
                .setParameter("deliveryTimeNextMonth", deliveryTimeNextMonth)
                .getResultList();
        return drivers;
    }
}
