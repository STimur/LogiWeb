package com.tsystems.javaschool.timber.logiweb.controller;

import com.tsystems.javaschool.timber.logiweb.dao.jpa.CityDaoJpa;
import com.tsystems.javaschool.timber.logiweb.dao.jpa.DriverDaoJpa;
import com.tsystems.javaschool.timber.logiweb.dao.jpa.OrderDaoJpa;
import com.tsystems.javaschool.timber.logiweb.dao.jpa.TruckDaoJpa;
import com.tsystems.javaschool.timber.logiweb.entity.*;
import com.tsystems.javaschool.timber.logiweb.exceptions.DoubleLoadCargoException;
import com.tsystems.javaschool.timber.logiweb.exceptions.NotAllCargosUnloadedException;
import com.tsystems.javaschool.timber.logiweb.exceptions.UnloadNotLoadedCargoException;
import com.tsystems.javaschool.timber.logiweb.service.CityService;
import com.tsystems.javaschool.timber.logiweb.service.DriverService;
import com.tsystems.javaschool.timber.logiweb.service.OrderService;
import com.tsystems.javaschool.timber.logiweb.service.TruckService;
import com.tsystems.javaschool.timber.logiweb.service.impl.CityServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.impl.DriverServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.impl.OrderServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.impl.TruckServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class Test
 */
public class OrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CityService cityService = new CityServiceImpl(new CityDaoJpa(City.class));
    private TruckService truckService = new TruckServiceImpl(new TruckDaoJpa(Truck.class));
    private DriverService driverService = new DriverServiceImpl(new DriverDaoJpa(Driver.class));

    private List<RoutePoint> route = new ArrayList<RoutePoint>();
    private Order orderToCreate = new Order();
    private List<Truck> suitableTrucks = new ArrayList<Truck>();
    private List<Driver> suitableDrivers = new ArrayList<Driver>();
    private List<City> cities = cityService.findAll();
    private Truck truckToAssign;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        OrderService orderService = new OrderServiceImpl(new OrderDaoJpa(Order.class));
        List<Order> orders = orderService.findAll();
        RequestDispatcher requestDispatcher;

        if (action != null) {
            switch (action) {
                case "list":
                    request.setAttribute("orders", orders);
                    requestDispatcher = getServletContext().getRequestDispatcher("/jsp/orders/orders.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                case "stateList":
                    request.setAttribute("orders", orders);
                    requestDispatcher = getServletContext().getRequestDispatcher("/jsp/orders/ordersState.jsp");
                    requestDispatcher.forward(request, response);
                    break;
            }
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        OrderService orderService = new OrderServiceImpl(new OrderDaoJpa(Order.class));
        int id;

        if (action != null) {
            switch (action) {
                case "list":
                    break;
                case "stateList": {
                    List<Order> orders = orderService.findAll();
                    request.setAttribute("orders", orders);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/orders/ordersState.jsp");
                    rd.forward(request, response);
                    break;
                }
                case "delete": {
                    id = parseOrderId(request);
                    orderService.delete(id);
                    break;
                }
                case "add": {
                    // for easy debug as this vars stay in memory
                    // because we have one servlet for all clients
                    route.clear();
                    orderToCreate = new Order();
                    truckToAssign = new Truck();
                    suitableDrivers.clear();
                    request.setAttribute("cities", cities);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/orders/addOrder.jsp");
                    rd.forward(request, response);
                    break;
                }
                case "addPoint": {
                    RoutePoint routePoint = parseRoutePoint(request);
                    Boolean isValidRoute = isValidRoute();
                    request.setAttribute("route", route);
                    request.setAttribute("cities", cities);
                    request.setAttribute("isValidRoute", isValidRoute.toString());
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/orders/addOrder.jsp");
                    rd.forward(request, response);
                    break;
                }
                case "getTrucks": {
                    orderToCreate.setRoute(route.get(0));
                    suitableTrucks = truckService.getSuitableTrucksForOrder(orderToCreate);
                    request.setAttribute("route", route);
                    request.setAttribute("cities", cities);
                    Boolean isValidRoute = isValidRoute();
                    request.setAttribute("isValidRoute", isValidRoute.toString());
                    request.setAttribute("order", orderToCreate);
                    request.setAttribute("trucks", suitableTrucks);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/orders/addOrder.jsp");
                    rd.forward(request, response);
                    break;
                }
                case "assignTruck": {
                    int truckId = Integer.valueOf(request.getParameter("truckToAssign"));
                    Truck truckToAssign = truckService.findById(truckId);
                    orderToCreate.setAssignedTruck(truckToAssign);
                    suitableDrivers = driverService.getSuitableDriversForOrder(orderToCreate);
                    request.setAttribute("route", route);
                    request.setAttribute("cities", cities);
                    Boolean isValidRoute = isValidRoute();
                    request.setAttribute("isValidRoute", isValidRoute.toString());
                    request.setAttribute("order", orderToCreate);
                    request.setAttribute("trucks", suitableTrucks);
                    request.setAttribute("drivers", suitableDrivers);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/orders/addOrder.jsp");
                    rd.forward(request, response);
                    break;
                }
                case "assignDriver": {
                    String[] params = request.getParameter("driverToAssign").split(" ");
                    int driverId = Integer.valueOf(params[0]);
                    int driverIndex = Integer.valueOf(params[1]);
                    Driver driver = driverService.findById(driverId);
                    if (orderToCreate.getAssignedDrivers() == null)
                        orderToCreate.setAssignedDrivers(new ArrayList<Driver>());
                    orderToCreate.getAssignedDrivers().add(driver);
                    driver.setOrder(orderToCreate);
                    driver.setCurrentTruck(orderToCreate.getAssignedTruck());
                    // it' s better to reget from db cause another client
                    // could have assigned these drivers
                    suitableDrivers.remove(driverIndex);

                    request.setAttribute("route", route);
                    request.setAttribute("cities", cities);
                    Boolean isValidRoute = isValidRoute();
                    request.setAttribute("isValidRoute", isValidRoute.toString());
                    request.setAttribute("order", orderToCreate);
                    request.setAttribute("trucks", suitableTrucks);
                    request.setAttribute("drivers", suitableDrivers);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/orders/addOrder.jsp");
                    rd.forward(request, response);
                    break;
                }
                case "create": {
                    try {
                        orderService.create(orderToCreate);
                    } catch (UnloadNotLoadedCargoException e) {
                        e.printStackTrace();
                    } catch (NotAllCargosUnloadedException e) {
                        e.printStackTrace();
                    } catch (DoubleLoadCargoException e) {
                        e.printStackTrace();
                    }
                    route.clear();
                    orderToCreate = new Order();
                    truckToAssign = new Truck();
                    suitableDrivers.clear();
                    response.sendRedirect("/Order?action=list");
                    return;
                }
            }
        }

        List<Order> orders = orderService.findAll();
        request.setAttribute("orders", orders);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/orders/orders.jsp");
        rd.forward(request, response);
    }

    private RoutePoint parseRoutePoint(HttpServletRequest request) {
        String name = request.getParameter("cargoName");
        int weight = Integer.valueOf(request.getParameter("cargoWeight"));
        Cargo cargo = new Cargo(name, weight);
        int cityId = Integer.valueOf(request.getParameter("pointCity"));
        RoutePointType pointType = RoutePointType.valueOf(request.getParameter("pointType"));
        City city = cityService.findById(cityId);
        RoutePoint routePoint = new RoutePoint(city, cargo, pointType);
        if (route.size() > 0)
            route.get(route.size() - 1).setNextRoutePoint(routePoint);
        route.add(routePoint);
        //a stub to to test persist to DB
        RoutePoint unloadPoint = new RoutePoint(city, cargo, RoutePointType.UNLOAD);
        if (route.size() > 0)
            route.get(route.size() - 1).setNextRoutePoint(unloadPoint);
        route.add(unloadPoint);
        return routePoint;
    }

    private int parseOrderId(HttpServletRequest request) {
        int id = Integer.valueOf(request.getParameter("id"));
        return id;
    }

    private Order parseOrder(HttpServletRequest request) {
        //some parsing staff
//        String name = request.getParameter("name");
//        String surname = request.getParameter("surname");
//        int hoursWorkedThisMonth = Integer.valueOf(request.getParameter("hoursWorkedThisMonth"));
//        int cityId = Integer.valueOf(request.getParameter("cityId"));
//        CityService cityService = new CityServiceImpl(new CityDaoJpa(City.class));
//        City city = cityService.findById(cityId);
//        Order order = new Order(name, surname, hoursWorkedThisMonth, state, city);
        Order order = new Order();
        return order;
    }

    boolean isValidRoute() {
        // TODO implement check of all BL here
        // absolutely must have even number of points
        int numOfPoints = route.size();
        if (numOfPoints > 0 && numOfPoints % 2 == 0)
            return true;
        return false;
    }
}
