package com.tsystems.javaschool.timber.logiweb.controller;

import com.tsystems.javaschool.timber.logiweb.dao.jpa.CityDaoJpa;
import com.tsystems.javaschool.timber.logiweb.dao.jpa.TruckDaoJpa;
import com.tsystems.javaschool.timber.logiweb.entity.City;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.service.CityService;
import com.tsystems.javaschool.timber.logiweb.service.impl.CityServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.TruckService;
import com.tsystems.javaschool.timber.logiweb.service.impl.TruckServiceImpl;

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
        TruckService truckService = new TruckServiceImpl(new TruckDaoJpa(Truck.class));
        List<Truck> trucks = truckService.findAll();
        request.setAttribute("trucks", trucks);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/trucks/trucks.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        TruckService truckService = new TruckServiceImpl(new TruckDaoJpa(Truck.class));
        int id;

        if (action != null) {
            switch (action) {
                case "create":
                    try {
                        Truck truck = parseTruck(request);
                        truckService.create(truck);
                    } catch (Exception ex) {
                        request.getSession().setAttribute("errorMessage", ex.toString());
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/error.jsp");
                        rd.forward(request, response);
                        return;
                    }
                    break;
                case "list":
                    break;
                case "delete":
                    id = parseTruckId(request);
                    truckService.delete(id);
                    break;
                case "edit":
                    try {
                        id = parseTruckId(request);
                        Truck truckToEdit = truckService.findById(id);
                        request.setAttribute("truckToEdit", truckToEdit);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/trucks/editTruck.jsp");
                        rd.forward(request, response);
                    } catch (Exception ex) {
                        request.getSession().setAttribute("errorMessage", ex.toString());
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/error.jsp");
                        rd.forward(request, response);
                        return;
                    }
                    break;
                case "update":
                    try {
                        Truck truckToUpdate = parseTruck(request);
                        id = parseTruckId(request);
                        truckToUpdate.setId(id);
                        truckService.update(truckToUpdate);
                    } catch (Exception ex) {
                        request.getSession().setAttribute("errorMessage", ex.toString());
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/error.jsp");
                        rd.forward(request, response);
                        return;
                    }
                    break;
            }
        }

        List<Truck> trucks = truckService.findAll();
        request.setAttribute("trucks", trucks);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/trucks/trucks.jsp");
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
        CityService cityService = new CityServiceImpl(new CityDaoJpa(City.class));
        City city = cityService.findById(cityId);
        Truck truck = new Truck(regNumber, shiftSize, capacity, state, city);
        return truck;
    }

}
