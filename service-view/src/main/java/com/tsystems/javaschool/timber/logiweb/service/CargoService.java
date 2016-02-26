package com.tsystems.javaschool.timber.logiweb.service;

import com.tsystems.javaschool.timber.logiweb.entity.Cargo;

import java.util.List;

/**
 * Created by timur_000 on 19.02.2016.
 */
public interface CargoService {
    void create(Cargo cargo);
    List<Cargo> findAll();
}
