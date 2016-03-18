package com.tsystems.javaschool.timber.logiweb.view.controllers;

import com.tsystems.javaschool.timber.logiweb.service.exceptions.DoubleLoadCargoException;
import com.tsystems.javaschool.timber.logiweb.service.exceptions.NotAllCargosUnloadedException;
import com.tsystems.javaschool.timber.logiweb.service.exceptions.OrderNotCreated;
import com.tsystems.javaschool.timber.logiweb.service.exceptions.UnloadNotLoadedCargoException;
import com.tsystems.javaschool.timber.logiweb.service.util.Services;
import com.tsystems.javaschool.timber.logiweb.view.exceptions.CargoValidationException;
import com.tsystems.javaschool.timber.logiweb.view.exceptions.IntegerOutOfRangeException;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.*;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.CityService;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.DriverService;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.OrderService;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.TruckService;
import com.tsystems.javaschool.timber.logiweb.view.util.InputParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

@Controller
//@SessionAttributes({"route", "orderToCreate", "suitableTrucks", "suitableDrivers", "cities", "loadedCargos"})
public class OrderController {
    private static final long serialVersionUID = 1L;

    final static Logger logger = Logger.getLogger(OrderController.class);
    @Autowired
    private CityService cityService;
    @Autowired
    private TruckService truckService;
    @Autowired
    private DriverService driverService;
    @Autowired
    private OrderService orderService;


    /*@ModelAttribute(value = "route")
    public List<RoutePoint> initRoute() {
        return new ArrayList<RoutePoint>();
    }
    @ModelAttribute(value = "orderToCreate")
    public Order createOrder() {
        return new Order();
    }
    @ModelAttribute(value = "suitableTrucks")
    public List<Truck> createSuitableTrucks() {
        return new ArrayList<Truck>();
    }
    @ModelAttribute(value = "suitableDrivers")
    public List<Driver> createSuitableDrivers() {
        return new ArrayList<Driver>();
    }
    @ModelAttribute(value = "cities")
    public List<City> getCities() {
        return cityService.findAll();
    }
    @ModelAttribute(value = "loadedCargos")
    public List<Cargo> initLoadedCargos() {
        return new ArrayList<Cargo>();
    }*/

    @RequestMapping("/orders")
    protected ModelAndView getOrders() throws ServletException, IOException {
        List<Order> orders = orderService.findAll();
        ModelAndView mv = new ModelAndView("manager/orders/orders");
        mv.addObject("orders", orders);
        return mv;
    }

    @RequestMapping("/orders-state")
    protected ModelAndView getOrdersState() throws ServletException, IOException {
        List<Order> orders = orderService.findAll();
        ModelAndView mv = new ModelAndView("manager/orders/ordersState");
        mv.addObject("orders", orders);
        return mv;
    }

    /*@RequestMapping("/orders/add")
    protected String addOrder(HttpServletRequest request) throws ServletException, IOException {
        clearAttributes(request.getSession());
        setAddOrderSessionAttributes(request.getSession());
        return "manager/orders/addOrder";
    }*/

    /*@RequestMapping("/orders/add-load-point")
    protected String addLoadPoint(HttpServletRequest request) {
        try {
            retrieveAddOrderSessionAttributes(request.getSession());
            orderToCreate = (Order) request.getSession().getAttribute("order");
            RoutePoint routePoint = parseRoutePoint(request);
            if (route.size() > 0)
                route.get(route.size() - 1).setNextRoutePoint(routePoint);
            route.add(routePoint);
            loadedCargos.add(routePoint.getCargo());
            setAddOrderSessionAttributes(request.getSession());
            return "manager/orders/addOrder";
        } catch (CargoValidationException ex) {
            request.setAttribute("cargoValidationException", ex);
            return "manager/orders/addOrder";
        } catch (Exception ex) {
            logger.error(ex.toString());
            request.getSession().setAttribute("errorMessage", ex.toString());
            return "error";
        }
    }

    @RequestMapping("/orders/add-unload-point")
    protected String addUnloadPoint(HttpServletRequest request) {
        retrieveAddOrderSessionAttributes(request.getSession());
        orderToCreate = (Order) request.getSession().getAttribute("order");
        RoutePoint routePoint = parseUnloadRoutePoint(request, loadedCargos);
        if (route.size() > 0)
            route.get(route.size() - 1).setNextRoutePoint(routePoint);
        route.add(routePoint);
        loadedCargos.remove(routePoint.getCargo());
        setAddOrderSessionAttributes(request.getSession());
        return "manager/orders/addOrder";
    }

    @RequestMapping("/orders/get-available-trucks")
    protected String getAvailableTrucks(HttpServletRequest request) {
        retrieveAddOrderSessionAttributes(request.getSession());
        orderToCreate = (Order) request.getSession().getAttribute("order");
        orderToCreate.setRoute(route.get(0));
        request.getSession().setAttribute("isRouteFormed", true);
        suitableTrucks = truckService.getSuitableTrucksForOrder(orderToCreate);
        setAddOrderSessionAttributes(request.getSession());
        return "manager/orders/addOrder";
    }

    @RequestMapping("/orders/assign-truck")
    protected String assignTruck(HttpServletRequest request) {
        retrieveAddOrderSessionAttributes(request.getSession());
        orderToCreate = (Order) request.getSession().getAttribute("order");
        int truckId = Integer.valueOf(request.getParameter("truckToAssign"));
        Truck truckToAssign = truckService.findById(truckId);
        orderToCreate.setAssignedTruck(truckToAssign);
        int deliveryTimeThisMonth = orderService.getDeliveryTimeThisMonth(orderToCreate);
        int deliveryTimeNextMonth = orderService.getDeliveryTimeNextMonth(orderToCreate);
        suitableDrivers = driverService.getSuitableDriversForOrder(orderToCreate, deliveryTimeThisMonth, deliveryTimeNextMonth);
        setAddOrderSessionAttributes(request.getSession());
        return "manager/orders/addOrder";
    }

    @RequestMapping("/orders/assign-driver")
    protected String assignDriver(HttpServletRequest request) {
        retrieveAddOrderSessionAttributes(request.getSession());
        orderToCreate = (Order) request.getSession().getAttribute("order");
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
        setAddOrderSessionAttributes(request.getSession());
        return "manager/orders/addOrder";
    }

    @RequestMapping("/orders/create")
    protected String createOrder(HttpServletRequest request) {
        retrieveAddOrderSessionAttributes(request.getSession());
        orderToCreate = (Order) request.getSession().getAttribute("order");
        try {
            orderService.create(orderToCreate);
        } catch (UnloadNotLoadedCargoException |
                NotAllCargosUnloadedException | DoubleLoadCargoException ex) {
            logger.error(ex.toString());
            request.getSession().setAttribute("errorMessage", ex.toString());
            return "error";
        } catch (OrderNotCreated ex) {
            logger.error(ex.toString());
            request.getSession().setAttribute("errorMessage", ex.getMessage());
            return "error";
        }
        clearAttributes(request.getSession());
        return "redirect:/orders";
    }

    @RequestMapping("/orders/delete")
    protected String deleteOrder(HttpServletRequest request) {
        int id = parseOrderId(request);
        try {
            orderService.delete(id);
        } catch (PersistenceException ex) {
            logger.error(ex.toString());
            request.getSession().setAttribute("errorMessage", ex.getMessage());
            return "error";
        }
        return "redirect:/orders";
    }

    private void clearAttributes(HttpSession session) {
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
        session.setAttribute("isRouteFormed", false);
        session.setAttribute("isTruckAssigned", false);
        session.setAttribute("isShiftFormed", false);
    }

    private void setAddOrderSessionAttributes(HttpSession session) {
        session.setAttribute("route", route);
        session.setAttribute("cities", cities);
        Boolean isValidRoute = isValidRoute(route);
        session.setAttribute("isValidRoute", isValidRoute.toString());
        session.setAttribute("order", orderToCreate);
        session.setAttribute("loadedCargos", loadedCargos);
        session.setAttribute("trucks", suitableTrucks);
        session.setAttribute("drivers", suitableDrivers);

        if (orderToCreate != null) {
            if (orderToCreate.getAssignedTruck() != null) {
                session.setAttribute("isTruckAssigned", true);
            }
            if (session.getAttribute("isTruckAssigned").equals(true))
                if (orderToCreate.getAssignedDrivers() != null)
                    if (orderToCreate.getAssignedDrivers().size() == orderToCreate.getAssignedTruck().getShiftSize()) {
                        session.setAttribute("isShiftFormed", true);
                    }
        }
    }

    private void retrieveAddOrderSessionAttributes(HttpSession session) {
        route = (List<RoutePoint>) session.getAttribute("route");
        loadedCargos = (List<Cargo>) session.getAttribute("loadedCargos");
        suitableTrucks = (List<Truck>) session.getAttribute("trucks");
        suitableDrivers = (List<Driver>) session.getAttribute("drivers");
    }*/

    private RoutePoint parseRoutePoint(HttpServletRequest request) throws CargoValidationException {
        CargoValidationException cargoValidationException = new CargoValidationException("");
        String name = "";
        int weight = 0;
        try {
            name = InputParser.parseLettersOnlyString(request.getParameter("cargoName"));
            cargoValidationException.getNameValidationUnit().setInputValue(name);
        } catch (PatternSyntaxException ex) {
            cargoValidationException.getNameValidationUnit().setValid(false);
            cargoValidationException.getNameValidationUnit().setInputValue(ex.getDescription());
            cargoValidationException.setValid(false);
        }
        try {
            weight = InputParser.parseNumber(request.getParameter("cargoWeight"), 1, 40000);
            cargoValidationException.getWeightValidationUnit().setInputValue(Integer.toString(weight));
        } catch (IntegerOutOfRangeException ex) {
            cargoValidationException.getWeightValidationUnit().setValid(false);
            cargoValidationException.getWeightValidationUnit().setInputValue(ex.getMessage());
            cargoValidationException.setValid(false);
        }
        if (!cargoValidationException.isValid())
            throw cargoValidationException;

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

    /**
     * Checks that number of loaded cargos is equal to number of unloaded.
     *
     * @param route contains of RoutePoints to check
     * @return true - if all loaded cargos are unloaded, false otherwise
     */
    boolean isValidRoute(List<RoutePoint> route) {
        int numOfRoutePoints = route.size();
        if (numOfRoutePoints > 0 && numOfRoutePoints % 2 == 0) {
            int numOfLoadPoints = 0;
            for (RoutePoint routePoint : route) {
                if (routePoint.getType() == RoutePointType.LOAD)
                    numOfLoadPoints++;
            }
            int numOfUnloadPoints = numOfRoutePoints - numOfLoadPoints;
            if (numOfLoadPoints == numOfUnloadPoints)
                return true;
        }
        return false;
    }
}
