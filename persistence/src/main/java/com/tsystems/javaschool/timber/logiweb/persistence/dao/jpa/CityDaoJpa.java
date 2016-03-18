package com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.CityDao;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.City;
import org.springframework.stereotype.Repository;

@Repository
public class CityDaoJpa extends GenericDaoJpa<City> implements CityDao {
}
