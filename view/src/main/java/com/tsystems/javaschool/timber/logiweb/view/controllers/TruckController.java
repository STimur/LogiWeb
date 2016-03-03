package com.tsystems.javaschool.timber.logiweb.view.controllers;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.CityDaoJpa;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.TruckDaoJpa;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.City;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.service.util.Services;
import com.tsystems.javaschool.timber.logiweb.view.exceptions.CapacityOutOfRangeException;
import com.tsystems.javaschool.timber.logiweb.view.exceptions.IntegerOutOfRangeException;
import com.tsystems.javaschool.timber.logiweb.view.exceptions.PlateNumberFormatException;
import com.tsystems.javaschool.timber.logiweb.view.exceptions.ShiftSizeOutOfRangeException;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.CityService;
import com.tsystems.javaschool.timber.logiweb.service.impl.CityServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.TruckService;
import com.tsystems.javaschool.timber.logiweb.service.impl.TruckServiceImpl;
import com.tsystems.javaschool.timber.logiweb.view.util.InputParser;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.regex.PatternSyntaxException;

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

    final static Logger logger = Logger.getLogger(TruckController.class);

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
        List<Truck> trucks = Services.getTruckService().findAll();
        request.setAttribute("trucks", trucks);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/trucks/trucks.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        int id;

        if (action != null) {
            switch (action) {
                case "create":
                    Truck truck = null;
                    try {
                        truck = parseTruck(request);
                        Services.getTruckService().create(truck);
                    } catch (ShiftSizeOutOfRangeException ex) {
                        request.setAttribute("shiftSizeOutOfRangeException", ex);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/trucks/addTruck.jsp");
                        rd.forward(request, response);
                    } catch (CapacityOutOfRangeException ex) {
                        request.setAttribute("capacityOutOfRangeException", ex);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/trucks/addTruck.jsp");
                        rd.forward(request, response);
                    } catch (PlateNumberFormatException ex) {
                        request.setAttribute("plateNumberFormatException", ex);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/trucks/addTruck.jsp");
                        rd.forward(request, response);
                    } catch (Exception ex) {
                        logger.error(ex.toString());
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
                    Services.getTruckService().delete(id);
                    break;
                case "edit":
                    try {
                        id = parseTruckId(request);
                        Truck truckToEdit = Services.getTruckService().findById(id);
                        request.setAttribute("truckToEdit", truckToEdit);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/trucks/editTruck.jsp");
                        rd.forward(request, response);
                    } catch (Exception ex) {
                        logger.error(ex.toString());
                        request.getSession().setAttribute("errorMessage", ex.toString());
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/error.jsp");
                        rd.forward(request, response);
                        return;
                    }
                    break;
                case "update":
                    try {
                        Truck updatedTruck = null;
                        updatedTruck = parseTruck(request);
                        id = parseTruckId(request);
                        updatedTruck.setId(id);
                        updateTruck(updatedTruck);
                    } catch (ShiftSizeOutOfRangeException ex) {
                        id = parseTruckId(request);
                        Truck truckToEdit = Services.getTruckService().findById(id);
                        request.setAttribute("truckToEdit", truckToEdit);
                        request.setAttribute("shiftSizeOutOfRangeException", ex);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/trucks/editTruck.jsp");
                        rd.forward(request, response);
                    } catch (CapacityOutOfRangeException ex) {
                        id = parseTruckId(request);
                        Truck truckToEdit = Services.getTruckService().findById(id);
                        request.setAttribute("truckToEdit", truckToEdit);
                        request.setAttribute("capacityOutOfRangeException", ex);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/trucks/editTruck.jsp");
                        rd.forward(request, response);
                    } catch (PlateNumberFormatException ex) {
                        id = parseTruckId(request);
                        Truck truckToEdit = Services.getTruckService().findById(id);
                        request.setAttribute("truckToEdit", truckToEdit);
                        request.setAttribute("plateNumberFormatException", ex);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/trucks/editTruck.jsp");
                        rd.forward(request, response);
                    } catch (Exception ex) {
                        logger.error(ex.toString());
                        request.getSession().setAttribute("errorMessage", ex.toString());
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/error.jsp");
                        rd.forward(request, response);
                        return;
                    }
                    break;
            }
        }

        List<Truck> trucks = Services.getTruckService().findAll();
        request.setAttribute("trucks", trucks);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/trucks/trucks.jsp");
        rd.forward(request, response);
    }

    private synchronized void updateTruck(Truck updatedTruck) {
        Services.getTruckService().update(updatedTruck);
    }

    private int parseTruckId(HttpServletRequest request) {
        int id = Integer.valueOf(request.getParameter("id"));
        return id;
    }

    private Truck parseTruck(HttpServletRequest request) throws ShiftSizeOutOfRangeException, CapacityOutOfRangeException, PlateNumberFormatException {
        String regNumber = "";
        try {
            regNumber = InputParser.parsePlateNumber(request.getParameter("regNumber"));
        } catch (PatternSyntaxException ex) {
            throw new PlateNumberFormatException();
        }
        int shiftSize = 0;
        try {
            shiftSize = InputParser.parseNumber(request.getParameter("shiftSize"), 1, 4);
        } catch (NumberFormatException | IntegerOutOfRangeException ex) {
            throw new ShiftSizeOutOfRangeException();
        }
        int capacity = 0;
        try {
            capacity = InputParser.parseNumber(request.getParameter("capacity"), 10, 40);
        } catch (NumberFormatException | IntegerOutOfRangeException ex) {
            throw new CapacityOutOfRangeException();
        }
        String state = request.getParameter("state");
        int cityId = Integer.valueOf(request.getParameter("cityId"));
        City city = Services.getCityService().findById(cityId);
        Truck truck = new Truck(regNumber, shiftSize, capacity, state, city);
        return truck;
    }
}
