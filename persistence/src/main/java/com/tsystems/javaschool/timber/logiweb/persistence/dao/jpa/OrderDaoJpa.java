package com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.OrderDao;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Order;

/**
 * Created by tims on 2/15/2016.
 */
public class OrderDaoJpa extends GenericDaoJpa<Order> implements OrderDao {

    public OrderDaoJpa(Class<Order> entityClass) {
        super(entityClass);
    }
}
