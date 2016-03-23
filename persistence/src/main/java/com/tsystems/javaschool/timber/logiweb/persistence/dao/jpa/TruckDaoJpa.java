package com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.TruckDao;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Order;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Truck;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TruckDaoJpa extends GenericDaoJpa<Truck> implements TruckDao {
    @Override
    public List<Truck> getSuitableTrucksForOrder(Order order) {
        List<Truck> trucks = (List<Truck>) getEntityManager()
                .createNamedQuery("findSuitableTrucks")
                .setParameter("maxLoad", (int)Math.ceil(order.calcMaxLoad()/1000)) // converting kgs to tonns and ceiling
                .getResultList();
        return trucks;
    }
}