package com.tsystems.javaschool.timber.logiweb.view.controllers;

import com.tsystems.javaschool.timber.logiweb.service.util.Services;
import com.tsystems.javaschool.timber.logiweb.view.exceptions.*;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.CityDaoJpa;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.DriverDaoJpa;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.City;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.DriverState;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.CityService;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.DriverService;
import com.tsystems.javaschool.timber.logiweb.service.impl.CityServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.impl.DriverServiceImpl;
import com.tsystems.javaschool.timber.logiweb.view.util.InputParser;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.PatternSyntaxException;

/**
 * Servlet implementation class Test
 */
public class DriverController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    final static Logger logger = Logger.getLogger(DriverController.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DriverController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.isUserInRole("manager")) {
            List<Driver> drivers = Services.getDriverService().findAll();
            request.setAttribute("drivers", drivers);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/drivers/drivers.jsp");
            rd.forward(request, response);
        } else {
            response.sendRedirect(getServletContext().getContextPath() + "/accessDenied.jsp");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String action = request.getParameter("action");
        DriverService driverService = Services.getDriverService();
        int id;

        if (action != null) {
            switch (action) {
                case "create": {
                    try {
                        Driver driver = parseDriver(request);
                        driverService.create(driver);
                    } catch (DriverValidationException ex) {
                        request.setAttribute("driverValidationException", ex);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/drivers/addDriver.jsp");
                        rd.forward(request, response);
                        return;
                    } catch (Exception ex) {
                        logger.error(ex.toString());
                        request.getSession().setAttribute("errorMessage", ex.toString());
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/error.jsp");
                        rd.forward(request, response);
                        return;
                    }
                    break;
                }
                case "list": {
                    break;
                }
                case "delete": {
                    try {
                        id = parseDriverId(request);
                        driverService.delete(id);
                        break;
                    } catch (DriverIdNotNumberException e) {
                        e.printStackTrace();
                    }
                }
                case "edit": {
                    try {
                        id = parseDriverId(request);
                        Driver driverToEdit = driverService.findById(id);
                        request.setAttribute("driverToEdit", driverToEdit);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/drivers/editDriver.jsp");
                        rd.forward(request, response);
                    } catch (Exception | DriverIdNotNumberException ex) {
                        logger.error(ex.toString());
                        request.getSession().setAttribute("errorMessage", ex.toString());
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/error.jsp");
                        rd.forward(request, response);
                        return;
                    }
                    break;
                }
                case "update": {
                    try {
                        Driver driverToUpdate = parseDriver(request);
                        try {
                            id = parseDriverId(request);
                            driverToUpdate.setId(id);
                            driverService.update(driverToUpdate);
                        } catch (DriverIdNotNumberException e) {
                            e.printStackTrace();
                        }
                    } catch (DriverValidationException ex) {
                        try {
                            id = parseDriverId(request);
                            Driver driverToEdit = driverService.findById(id);
                            request.setAttribute("driverToEdit", driverToEdit);
                            request.setAttribute("notNameException", ex);
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/drivers/editDriver.jsp");
                            rd.forward(request, response);
                        } catch (DriverIdNotNumberException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception ex) {
                        logger.error(ex.toString());
                        request.getSession().setAttribute("errorMessage", ex.toString());
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/error.jsp");
                        rd.forward(request, response);
                        return;
                    }
                    break;
                }
                case "getJobInfo": {
                    try {
                        id = parseDriverId(request);
                        Driver driver = driverService.findById(id);
                        request.setAttribute("driver", driver);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/driver/driverJobInfo.jsp");
                        rd.forward(request, response);
                    } catch (DriverIdNotNumberException ex) {
                        request.setAttribute("driverIdNotNumberException", ex);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/driver/driverGetJobInfo.jsp");
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
        }

        List<Driver> drivers = driverService.findAll();
        request.setAttribute("drivers", drivers);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/drivers/drivers.jsp");
        rd.forward(request, response);
    }

    private int parseDriverId(HttpServletRequest request) throws DriverIdNotNumberException {
        int id = 0;
        // this validation needed for case when driver enters his id to get info about his order
        try {
            id = InputParser.parseNumber(request.getParameter("id"), 1, Integer.MAX_VALUE);
            return id;
        } catch (NumberFormatException | IntegerOutOfRangeException ex) {
            throw new DriverIdNotNumberException();
        }
    }

    private Driver parseDriver(HttpServletRequest request) throws DriverValidationException {
        DriverValidationException driverValidationException = new DriverValidationException("");
        String name = "";
        String surname = "";
        int hoursWorkedThisMonth = 0;
        try {
            name = InputParser.parseLettersOnlyString(request.getParameter("name"));
            driverValidationException.getNameValidationUnit().setInputValue(name);
        } catch (PatternSyntaxException ex) {
            driverValidationException.getNameValidationUnit().setValid(false);
            driverValidationException.getNameValidationUnit().setInputValue(ex.getDescription());
            driverValidationException.setValid(false);
        }
        try {
            surname = InputParser.parseLettersOnlyString(request.getParameter("surname"));
            driverValidationException.getSurnameValidationUnit().setInputValue(surname);
        } catch (PatternSyntaxException ex) {
            driverValidationException.getSurnameValidationUnit().setValid(false);
            driverValidationException.getSurnameValidationUnit().setInputValue(ex.getDescription());
            driverValidationException.setValid(false);
        }
        try {
            hoursWorkedThisMonth = InputParser.parseNumber(request.getParameter("hoursWorkedThisMonth"), 0, 176);
            driverValidationException.getHoursOfWorkValidationUnit().setInputValue(Integer.toString(hoursWorkedThisMonth));
        } catch (IntegerOutOfRangeException | NumberFormatException ex) {
            driverValidationException.getHoursOfWorkValidationUnit().setValid(false);
            driverValidationException.getHoursOfWorkValidationUnit().setInputValue(ex.getMessage());
            driverValidationException.setValid(false);
        }
        if (!driverValidationException.isValid())
            throw driverValidationException;

        DriverState state = DriverState.valueOf(request.getParameter("state"));
        int cityId = Integer.valueOf(request.getParameter("cityId"));
        City city = Services.getCityService().findById(cityId);
        Driver driver = new Driver(name, surname, hoursWorkedThisMonth, state, city);
        return driver;
    }

}
