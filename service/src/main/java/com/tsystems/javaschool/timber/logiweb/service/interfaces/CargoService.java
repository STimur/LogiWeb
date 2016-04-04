package com.tsystems.javaschool.timber.logiweb.service.interfaces;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.Cargo;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.service.dto.CargoDto;

import java.util.List;

/**
 * Business logic related to Cargo entity.
 * @author Timur Salakhetdinov
 */
public interface CargoService {
    /**
     * Create Cargo entity in database.
     */
    void create(Cargo cargo);

    /**
     * Update Cargo entity in database.
     */
    void update(Cargo cargo);

    /**
     * Find Cargo in database.
     * @param id id of the cargo in database
     * @return found cargo, null if not found
     */
    Cargo findById(int id);

    /**
     * Find cargos.
     * @return List of all cargos in database.
     */
    List<Cargo> findAll();

    /**
     * Change state for cargo
     * @param cargo contains cargo id and state which will be set
     * @return Cargo for which state have been changed
     */
    Cargo changeState(CargoDto cargo);
}
