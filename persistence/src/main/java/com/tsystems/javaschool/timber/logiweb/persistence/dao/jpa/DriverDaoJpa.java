package com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.DriverDao;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Order;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.util.JpaUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by tims on 2/15/2016.
 */
@Repository
public class DriverDaoJpa extends GenericDaoJpa<Driver> implements DriverDao {
    @Override
    public List<Driver> getSuitableDriversForOrder(Order order, int deliveryTimeThisMonth, int deliveryTimeNextMonth) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        List<Driver> drivers = (List<Driver>) entityManager
                .createNamedQuery("findSuitableDrivers")
                .setParameter("assignedTruckCity", order.getAssignedTruck().getCity())
                .setParameter("deliveryTimeThisMonth", deliveryTimeThisMonth)
                .setParameter("deliveryTimeNextMonth", deliveryTimeNextMonth)
                .getResultList();
        return drivers;
    }
}
