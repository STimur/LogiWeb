package com.tsystems.javaschool.timber.logiweb.controller;

import com.tsystems.javaschool.timber.logiweb.dao.jpa.CityDaoJpa;
import com.tsystems.javaschool.timber.logiweb.dao.jpa.TruckDaoJpa;
import com.tsystems.javaschool.timber.logiweb.entity.City;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.service.CityService;
import com.tsystems.javaschool.timber.logiweb.service.TruckService;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Test
 */
public class TruckController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TruckController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TruckService truckService = new TruckService(new TruckDaoJpa(Truck.class));
        List<Truck> trucks = truckService.findAll();
        request.setAttribute("trucks", trucks);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/trucks.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        TruckService truckService = new TruckService(new TruckDaoJpa(Truck.class));
        int id;

        if (action != null) {
            switch (action) {
                case "create":
                    Truck truck = parseTruck(request);
                    truckService.create(truck);
                    break;
                case "list":
                    break;
                case "delete":
                    id = parseTruckId(request);
                    truckService.delete(id);
                    break;
                case "edit":
                    id = parseTruckId(request);
                    Truck truckToEdit = truckService.findById(id);
                    request.setAttribute("truckToEdit", truckToEdit);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/editTruck.jsp");
                    rd.forward(request, response);
                    break;
                case "update":
                    Truck truckToUpdate = parseTruck(request);
                    id = parseTruckId(request);
                    truckToUpdate.setId(id);
                    truckService.update(truckToUpdate);
                    break;
            }
        }

        List<Truck> trucks = truckService.findAll();
        request.setAttribute("trucks", trucks);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/trucks.jsp");
        rd.forward(request, response);
    }

    private int parseTruckId(HttpServletRequest request) {
        int id = Integer.valueOf(request.getParameter("id"));
        return id;
    }

    private Truck parseTruck(HttpServletRequest request) {
        String regNumber = request.getParameter("regNumber");
        int shiftSize = Integer.valueOf(request.getParameter("shiftSize"));
        int capacity = Integer.valueOf(request.getParameter("capacity"));
        String state = request.getParameter("state");
        int cityId = Integer.valueOf(request.getParameter("cityId"));
        CityService cityService = new CityService(new CityDaoJpa(City.class));
        City city = cityService.findById(cityId);
        Truck truck = new Truck(regNumber, shiftSize, capacity, state, city);
        return truck;
    }

}
