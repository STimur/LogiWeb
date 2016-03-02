package com.tsystems.javaschool.timber.logiweb.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.dao.interfaces.TruckDao;
import com.tsystems.javaschool.timber.logiweb.entity.Order;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.dao.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class TruckDaoJpa extends GenericDaoJpa<Truck> implements TruckDao {

    public TruckDaoJpa(Class<Truck> entityClass) {
        super(entityClass);
    }

    @Override
    public List<Truck> getSuitableTrucksForOrder(Order order) {
        EntityManager em = JpaUtil.getEntityManager();
        List<Truck> trucks = (List<Truck>) em
                .createNamedQuery("findSuitableTrucks")
                .setParameter("maxLoad", (int)Math.ceil(order.calcMaxLoad()/1000)) // converting kgs to tonns and ceiling
                .getResultList();
        return trucks;
    }
}