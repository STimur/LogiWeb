package com.tsystems.javaschool.timber.logiweb.controller;

import com.tsystems.javaschool.timber.logiweb.controller.exceptions.*;
import com.tsystems.javaschool.timber.logiweb.dao.jpa.CityDaoJpa;
import com.tsystems.javaschool.timber.logiweb.dao.jpa.DriverDaoJpa;
import com.tsystems.javaschool.timber.logiweb.entity.City;
import com.tsystems.javaschool.timber.logiweb.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.entity.DriverState;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.CityService;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.DriverService;
import com.tsystems.javaschool.timber.logiweb.service.impl.CityServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.impl.DriverServiceImpl;
import com.tsystems.javaschool.timber.logiweb.controller.util.InputParser;
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
            DriverService driverService = new DriverServiceImpl(new DriverDaoJpa(Driver.class));
            List<Driver> drivers = driverService.findAll();
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
        DriverService driverService = new DriverServiceImpl(new DriverDaoJpa(Driver.class));
        int id;

        if (action != null) {
            switch (action) {
                case "create": {
                    try {
                        Driver driver = parseDriver(request);
                        driverService.create(driver);
                    } catch (NotNameException ex) {
                        request.setAttribute("notNameException", ex);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/drivers/addDriver.jsp");
                        rd.forward(request, response);
                        return;
                    } catch (NotSurnameException ex) {
                        request.setAttribute("notSurnameException", ex);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/drivers/addDriver.jsp");
                        rd.forward(request, response);
                        return;
                    } catch (HoursWorkedOutOfRangeException ex) {
                        request.setAttribute("hoursWorkedOutOfRangeException", ex);
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
                    } catch (NotNameException ex) {
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
                    } catch (NotSurnameException ex) {
                        try {
                            id = parseDriverId(request);
                            Driver driverToEdit = driverService.findById(id);
                            request.setAttribute("driverToEdit", driverToEdit);
                            request.setAttribute("notSurnameException", ex);
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/drivers/editDriver.jsp");
                            rd.forward(request, response);
                        } catch (DriverIdNotNumberException e) {
                            e.printStackTrace();
                        }
                    } catch (HoursWorkedOutOfRangeException ex) {
                        try {
                            id = parseDriverId(request);
                            Driver driverToEdit = driverService.findById(id);
                            request.setAttribute("driverToEdit", driverToEdit);
                            request.setAttribute("hoursWorkedOutOfRangeException", ex);
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
        try {
            id = InputParser.parseNumber(request.getParameter("id"), 1, Integer.MAX_VALUE);
            return id;
        } catch (NumberFormatException | IntegerOutOfRangeException ex) {
            throw new DriverIdNotNumberException();
        }
    }

    private Driver parseDriver(HttpServletRequest request) throws NotNameException, NotSurnameException, HoursWorkedOutOfRangeException {
        String name = "";
        String surname = "";
        int hoursWorkedThisMonth = 0;
        try {
            name = InputParser.parseLettersOnlyString(request.getParameter("name"));
        } catch (PatternSyntaxException ex) {
            throw new NotNameException();
        }
        try {
            surname = InputParser.parseLettersOnlyString(request.getParameter("surname"));
            hoursWorkedThisMonth = InputParser.parseNumber(request.getParameter("hoursWorkedThisMonth"), 0, 176);
        } catch (PatternSyntaxException ex) {
            throw new NotSurnameException();
        } catch (IntegerOutOfRangeException | NumberFormatException ex) {
            throw new HoursWorkedOutOfRangeException();
        }

        DriverState state = DriverState.valueOf(request.getParameter("state"));
        int cityId = Integer.valueOf(request.getParameter("cityId"));
        CityService cityService = new CityServiceImpl(new CityDaoJpa(City.class));
        City city = cityService.findById(cityId);
        Driver driver = new Driver(name, surname, hoursWorkedThisMonth, state, city);
        return driver;
    }

}
