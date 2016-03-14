package com.tsystems.javaschool.timber.logiweb.view.controllers;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.CityDaoJpa;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.TruckDaoJpa;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.City;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.service.util.Services;
import com.tsystems.javaschool.timber.logiweb.view.exceptions.*;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.CityService;
import com.tsystems.javaschool.timber.logiweb.service.impl.CityServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.TruckService;
import com.tsystems.javaschool.timber.logiweb.service.impl.TruckServiceImpl;
import com.tsystems.javaschool.timber.logiweb.view.util.InputParser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TruckController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static List<City> cities = Services.getCityService().findAll();

    final static Logger logger = Logger.getLogger(TruckController.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TruckController() {
        super();
        // TODO Auto-generated constructor stub
    }


    @RequestMapping("/trucks")
    protected ModelAndView getTrucks() throws ServletException, IOException {
        List<Truck> trucks = Services.getTruckService().findAll();
        ModelAndView mv = new ModelAndView("manager/trucks/trucks");
        mv.addObject("trucks", trucks);
        return mv;
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        int id;

        if (action != null) {
            switch (action) {
                case "add": {
                    request.setAttribute("cities", cities);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/trucks/addTruck.jsp");
                    rd.forward(request, response);
                }
                case "create":
                    Truck truck = null;
                    try {
                        truck = parseTruck(request);
                        Services.getTruckService().create(truck);
                    } catch (TruckValidationException ex) {
                        request.setAttribute("truckValidationException", ex);
                        request.setAttribute("cities", cities);
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
                        request.setAttribute("cities", cities);
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
                    } catch (TruckValidationException ex) {
                        id = parseTruckId(request);
                        Truck truckToEdit = Services.getTruckService().findById(id);
                        request.setAttribute("truckToEdit", truckToEdit);
                        request.setAttribute("cities", cities);
                        request.setAttribute("truckValidationException", ex);
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

    private Truck parseTruck(HttpServletRequest request) throws TruckValidationException {
        TruckValidationException truckValidationException = new TruckValidationException("");
        String regNumber = "";
        try {
            regNumber = InputParser.parsePlateNumber(request.getParameter("regNumber"));
            truckValidationException.getPlateNumberValidationUnit().setInputValue(regNumber);
        } catch (PatternSyntaxException ex) {
            truckValidationException.getPlateNumberValidationUnit().setValid(false);
            truckValidationException.getPlateNumberValidationUnit().setInputValue(ex.getDescription());
            truckValidationException.setValid(false);
        }
        int shiftSize = 0;
        try {
            shiftSize = InputParser.parseNumber(request.getParameter("shiftSize"), 1, 4);
            truckValidationException.getShiftSizeValidationUnit().setInputValue(Integer.toString(shiftSize));
        } catch (NumberFormatException | IntegerOutOfRangeException ex) {
            truckValidationException.getShiftSizeValidationUnit().setValid(false);
            truckValidationException.getShiftSizeValidationUnit().setInputValue(ex.getMessage());
            truckValidationException.setValid(false);
        }
        int capacity = 0;
        try {
            capacity = InputParser.parseNumber(request.getParameter("capacity"), 10, 40);
            truckValidationException.getCapacityValidationUnit().setInputValue(Integer.toString(capacity));
        } catch (NumberFormatException | IntegerOutOfRangeException ex) {
            truckValidationException.getCapacityValidationUnit().setValid(false);
            truckValidationException.getCapacityValidationUnit().setInputValue(ex.getMessage());
            truckValidationException.setValid(false);
        }
        if (!truckValidationException.isValid())
            throw truckValidationException;
        String state = request.getParameter("state");
        int cityId = Integer.valueOf(request.getParameter("cityId"));
        City city = Services.getCityService().findById(cityId);
        Truck truck = new Truck(regNumber, shiftSize, capacity, state, city);
        return truck;
    }
}
