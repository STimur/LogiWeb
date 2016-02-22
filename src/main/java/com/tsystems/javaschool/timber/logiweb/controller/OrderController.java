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
import javax.servlet.http.HttpSession;
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
                    requestDispatcher = getServletContext().getRequestDispatcher("/jsp/manager/orders/orders.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                case "stateList":
                    request.setAttribute("orders", orders);
                    requestDispatcher = getServletContext().getRequestDispatcher("/jsp/manager/orders/ordersState.jsp");
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

        List<RoutePoint> route = new ArrayList<RoutePoint>();
        Order orderToCreate = new Order();
        List<Truck> suitableTrucks = new ArrayList<Truck>();
        List<Driver> suitableDrivers = new ArrayList<Driver>();
        List<City> cities = cityService.findAll();
        List<Cargo> loadedCargos = new ArrayList<Cargo>();

        if (action != null) {
            switch (action) {
                case "list":
                    break;
                case "stateList": {
                    List<Order> orders = orderService.findAll();
                    request.setAttribute("orders", orders);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/orders/ordersState.jsp");
                    rd.forward(request, response);
                    break;
                }
                case "delete": {
                    id = parseOrderId(request);
                    orderService.delete(id);
                    break;
                }
                case "add": {
                    //-----------
                    // for easy debug as this vars stay in memory
                    // because we have one servlet for all clients
                    //clearAttributes();
                    //-----------
                    setAddOrderSessionAttributes(request.getSession(), route, orderToCreate, suitableTrucks,
                            suitableDrivers, cities, loadedCargos);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/orders/addOrder.jsp");
                    rd.forward(request, response);
                    break;
                }
                case "addLoadPoint": {
                    retrieveAddOrderSessionAttributes(request.getSession(), route, orderToCreate, suitableTrucks,
                            suitableDrivers, cities, loadedCargos);
                    orderToCreate = (Order)request.getSession().getAttribute("order");
                    RoutePoint routePoint = parseRoutePoint(request);
                    if (route.size() > 0)
                        route.get(route.size() - 1).setNextRoutePoint(routePoint);
                    route.add(routePoint);
                    loadedCargos.add(routePoint.getCargo());
                    setAddOrderSessionAttributes(request.getSession(), route, orderToCreate, suitableTrucks,
                            suitableDrivers, cities, loadedCargos);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/orders/addOrder.jsp");
                    rd.forward(request, response);
                    break;
                }
                case "addUnloadPoint": {
                    retrieveAddOrderSessionAttributes(request.getSession(), route, orderToCreate, suitableTrucks,
                            suitableDrivers, cities, loadedCargos);
                    orderToCreate = (Order)request.getSession().getAttribute("order");
                    RoutePoint routePoint = parseUnloadRoutePoint(request, loadedCargos);
                    if (route.size() > 0)
                        route.get(route.size() - 1).setNextRoutePoint(routePoint);
                    route.add(routePoint);
                    loadedCargos.remove(routePoint.getCargo());
                    setAddOrderSessionAttributes(request.getSession(), route, orderToCreate, suitableTrucks,
                            suitableDrivers, cities, loadedCargos);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/orders/addOrder.jsp");
                    rd.forward(request, response);
                    break;
                }
                case "getTrucks": {
                    retrieveAddOrderSessionAttributes(request.getSession(), route, orderToCreate, suitableTrucks,
                            suitableDrivers, cities, loadedCargos);
                    orderToCreate = (Order)request.getSession().getAttribute("order");
                    orderToCreate.setRoute(route.get(0));
                    suitableTrucks = truckService.getSuitableTrucksForOrder(orderToCreate);
                    setAddOrderSessionAttributes(request.getSession(), route, orderToCreate, suitableTrucks,
                            suitableDrivers, cities, loadedCargos);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/orders/addOrder.jsp");
                    rd.forward(request, response);
                    break;
                }
                case "assignTruck": {
                    retrieveAddOrderSessionAttributes(request.getSession(), route, orderToCreate, suitableTrucks,
                            suitableDrivers, cities, loadedCargos);
                    orderToCreate = (Order)request.getSession().getAttribute("order");
                    int truckId = Integer.valueOf(request.getParameter("truckToAssign"));
                    Truck truckToAssign = truckService.findById(truckId);
                    orderToCreate.setAssignedTruck(truckToAssign);
                    int deliveryTimeThisMonth = orderService.getDeliveryTimeThisMonth(orderToCreate);
                    int deliveryTimeNextMonth = orderService.getDeliveryTimeNextMonth(orderToCreate);
                    suitableDrivers = driverService.getSuitableDriversForOrder(orderToCreate, deliveryTimeThisMonth, deliveryTimeNextMonth);
                    setAddOrderSessionAttributes(request.getSession(), route, orderToCreate, suitableTrucks,
                            suitableDrivers, cities, loadedCargos);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/orders/addOrder.jsp");
                    rd.forward(request, response);
                    break;
                }
                case "assignDriver": {
                    retrieveAddOrderSessionAttributes(request.getSession(), route, orderToCreate, suitableTrucks,
                            suitableDrivers, cities, loadedCargos);
                    orderToCreate = (Order)request.getSession().getAttribute("order");
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
                    setAddOrderSessionAttributes(request.getSession(), route, orderToCreate, suitableTrucks,
                            suitableDrivers, cities, loadedCargos);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/orders/addOrder.jsp");
                    rd.forward(request, response);
                    break;
                }
                case "create": {
                    retrieveAddOrderSessionAttributes(request.getSession(), route, orderToCreate, suitableTrucks,
                            suitableDrivers, cities, loadedCargos);
                    orderToCreate = (Order)request.getSession().getAttribute("order");
                    try {
                        orderService.create(orderToCreate);
                    } catch (UnloadNotLoadedCargoException e) {
                        e.printStackTrace();
                    } catch (NotAllCargosUnloadedException e) {
                        e.printStackTrace();
                    } catch (DoubleLoadCargoException e) {
                        e.printStackTrace();
                    }
                    clearAttributes(request.getSession(), route, orderToCreate, suitableTrucks,
                            suitableDrivers, cities, loadedCargos);
                    response.sendRedirect("/Order?action=list");
                    return;
                }
            }
        }

        List<Order> orders = orderService.findAll();
        request.setAttribute("orders", orders);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/manager/orders/orders.jsp");
        rd.forward(request, response);
    }

    private void clearAttributes(HttpSession session, List<RoutePoint> route, Order orderToCreate,
                                 List<Truck> suitableTrucks, List<Driver> suitableDrivers,
                                 List<City> cities, List<Cargo> loadedCargos) {
        route.clear();
        session.setAttribute("route", route);
        orderToCreate = new Order();
        session.setAttribute("order", orderToCreate);
        suitableDrivers.clear();
        session.setAttribute("drivers", suitableDrivers);
        suitableTrucks.clear();
        session.setAttribute("trucks", suitableTrucks);
        loadedCargos.clear();
        session.setAttribute("loadedCargos", loadedCargos);
    }

    private void setAddOrderSessionAttributes(HttpSession session, List<RoutePoint> route,
                                              Order orderToCreate, List<Truck> suitableTrucks,
                                              List<Driver> suitableDrivers, List<City> cities, List<Cargo> loadedCargos) {
        session.setAttribute("route", route);
        session.setAttribute("cities", cities);
        Boolean isValidRoute = isValidRoute(route);
        session.setAttribute("isValidRoute", isValidRoute.toString());
        session.setAttribute("order", orderToCreate);
        session.setAttribute("loadedCargos", loadedCargos);
        session.setAttribute("trucks", suitableTrucks);
        session.setAttribute("drivers", suitableDrivers);
    }

    private void retrieveAddOrderSessionAttributes(HttpSession session, List<RoutePoint> route,
                                                   Order orderToCreate, List<Truck> suitableTrucks,
                                                   List<Driver> suitableDrivers, List<City> cities, List<Cargo> loadedCargos) {
        route.addAll((List<RoutePoint>)session.getAttribute("route"));
        loadedCargos.addAll((List<Cargo>)session.getAttribute("loadedCargos"));
        suitableTrucks.addAll((List<Truck>)session.getAttribute("trucks"));
        suitableDrivers.addAll((List<Driver>)session.getAttribute("drivers"));
    }

    private RoutePoint parseRoutePoint(HttpServletRequest request) {
        String name = request.getParameter("cargoName");
        int weight = Integer.valueOf(request.getParameter("cargoWeight"));
        Cargo cargo = new Cargo(name, weight);
        int cityId = Integer.valueOf(request.getParameter("pointCity"));
        RoutePointType pointType = RoutePointType.valueOf(request.getParameter("pointType"));
        City city = cityService.findById(cityId);
        RoutePoint routePoint = new RoutePoint(city, cargo, pointType);
        return routePoint;
    }

    private RoutePoint parseUnloadRoutePoint(HttpServletRequest request, List<Cargo> loadedCargos) {
        String[] params = request.getParameter("cargoToUnload").split(" ");
        int cargoId = Integer.valueOf(params[0]);
        int cargoIndex = Integer.valueOf(params[1]);
        Cargo cargoToUnload = loadedCargos.get(cargoIndex);
        int cityId = Integer.valueOf(request.getParameter("unloadPointCityId"));
        City city = cityService.findById(cityId);
        RoutePoint routePoint = new RoutePoint(city, cargoToUnload, RoutePointType.UNLOAD);
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

    boolean isValidRoute(List<RoutePoint> route) {
        // TODO implement check of all BL here
        // absolutely must have even number of points
        int numOfRoutePoints = route.size();
        if (numOfRoutePoints > 0 && numOfRoutePoints % 2 == 0) {
            int numOfLoadPoints = 0;
            for (RoutePoint routePoint: route) {
                if (routePoint.getType()==RoutePointType.LOAD)
                    numOfLoadPoints++;
            }
            int numOfUnloadPoints =  numOfRoutePoints - numOfLoadPoints;
            if (numOfLoadPoints == numOfUnloadPoints)
                return true;
        }
        return false;
    }
}
