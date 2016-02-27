package com.tsystems.javaschool.timber.logiweb.controller;

import com.tsystems.javaschool.timber.logiweb.dao.jpa.CityDaoJpa;
import com.tsystems.javaschool.timber.logiweb.dao.jpa.DriverDaoJpa;
import com.tsystems.javaschool.timber.logiweb.entity.City;
import com.tsystems.javaschool.timber.logiweb.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.entity.DriverState;
import com.tsystems.javaschool.timber.logiweb.service.CityService;
import com.tsystems.javaschool.timber.logiweb.service.DriverService;
import com.tsystems.javaschool.timber.logiweb.service.impl.CityServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.impl.DriverServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
        //if (request.isUserInRole("manager")) {
            DriverService driverService = new DriverServiceImpl(new DriverDaoJpa(Driver.class));
            List<Driver> drivers = driverService.findAll();
            request.setAttribute("drivers", drivers);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/drivers/drivers.jsp");
            rd.forward(request, response);
        //} else {
        //    response.sendRedirect("/accessDenied.jsp");
        //}
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
                    id = parseDriverId(request);
                    driverService.delete(id);
                    break;
                }
                case "edit": {
                    try {
                        id = parseDriverId(request);
                        Driver driverToEdit = driverService.findById(id);
                        request.setAttribute("driverToEdit", driverToEdit);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/drivers/editDriver.jsp");
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
                case "update": {
                    try {
                        Driver driverToUpdate = parseDriver(request);
                        id = parseDriverId(request);
                        driverToUpdate.setId(id);
                        driverService.update(driverToUpdate);
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

    private int parseDriverId(HttpServletRequest request) {
        int id = Integer.valueOf(request.getParameter("id"));
        return id;
    }

    private Driver parseDriver(HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        int hoursWorkedThisMonth = Integer.valueOf(request.getParameter("hoursWorkedThisMonth"));
        DriverState state = DriverState.valueOf(request.getParameter("state"));
        int cityId = Integer.valueOf(request.getParameter("cityId"));
        CityService cityService = new CityServiceImpl(new CityDaoJpa(City.class));
        City city = cityService.findById(cityId);
        Driver driver = new Driver(name, surname, hoursWorkedThisMonth, state, city);
        return driver;
    }

}
