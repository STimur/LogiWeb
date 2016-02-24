package com.tsystems.javaschool.timber.logiweb.controller;

import com.tsystems.javaschool.timber.logiweb.dao.jpa.CityDaoJpa;
import com.tsystems.javaschool.timber.logiweb.dao.jpa.DriverDaoJpa;
import com.tsystems.javaschool.timber.logiweb.entity.City;
import com.tsystems.javaschool.timber.logiweb.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.entity.DriverState;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.service.CityService;
import com.tsystems.javaschool.timber.logiweb.service.DriverService;
import com.tsystems.javaschool.timber.logiweb.service.impl.CityServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.impl.DriverServiceImpl;

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
        }
        else {
            response.sendRedirect("/accessDenied.jsp");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String action = request.getParameter("action");
            DriverService driverService = new DriverServiceImpl(new DriverDaoJpa(Driver.class));
            int id;

            if (action != null) {
                switch (action) {
                    case "create": {
                        Driver driver = parseDriver(request);
                        driverService.create(driver);
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
                        id = parseDriverId(request);
                        Driver driverToEdit = driverService.findById(id);
                        request.setAttribute("driverToEdit", driverToEdit);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/drivers/editDriver.jsp");
                        rd.forward(request, response);
                        break;
                    }
                    case "update": {
                        Driver driverToUpdate = parseDriver(request);
                        id = parseDriverId(request);
                        driverToUpdate.setId(id);
                        driverService.update(driverToUpdate);
                        break;
                    }
                    case "getJobInfo": {
                        id = parseDriverId(request);
                        Driver driver = driverService.findById(id);
                        request.setAttribute("driver", driver);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/driver/driverJobInfo.jsp");
                        rd.forward(request, response);
                        break;
                    }
                }
            }

            List<Driver> drivers = driverService.findAll();
            request.setAttribute("drivers", drivers);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/drivers/drivers.jsp");
            rd.forward(request, response);
        } catch (NumberFormatException ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
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
