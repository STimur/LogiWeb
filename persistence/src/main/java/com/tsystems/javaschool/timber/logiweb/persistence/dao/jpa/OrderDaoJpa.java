package com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.OrderDao;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Order;
import org.springframework.stereotype.Repository;

/**
 * Created by tims on 2/15/2016.
 */
@Repository
public class OrderDaoJpa extends GenericDaoJpa<Order> implements OrderDao {
}
