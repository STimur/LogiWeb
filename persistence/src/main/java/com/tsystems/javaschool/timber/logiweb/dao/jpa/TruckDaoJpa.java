package com.tsystems.javaschool.timber.logiweb.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.dao.TruckDao;
import com.tsystems.javaschool.timber.logiweb.entity.Order;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;

import java.util.List;

public class TruckDaoJpa extends GenericDaoJpa<Truck> implements TruckDao {

    public TruckDaoJpa(Class<Truck> entityClass) {
        super(entityClass);
    }

    @Override
    public List<Truck> getSuitableTrucksForOrder(Order order) {
        openEntityManager();
        List<Truck> trucks = (List<Truck>) getEntityManager()
                .createNamedQuery("findSuitableTrucks")
                .setParameter("maxLoad", order.calcMaxLoad())
                .getResultList();
        closeEntityManager();
        return trucks;
    }
}