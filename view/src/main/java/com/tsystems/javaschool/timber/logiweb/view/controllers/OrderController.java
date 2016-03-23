package com.tsystems.javaschool.timber.logiweb.view.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.DefaultSessionAttributeStore;
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.context.request.WebRequest;
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
@SessionAttributes({"route", "orderToCreate", "suitableTrucks", "suitableDrivers", "cities", "loadedCargos"})
public class OrderController {
    final static Logger logger = Logger.getLogger(OrderController.class);
    @Autowired
    private CityService cityService;
    @Autowired
    private TruckService truckService;
    @Autowired
    private DriverService driverService;
    @Autowired
    private OrderService orderService;

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

    @RequestMapping("/orders/add")
    protected ModelAndView addOrder(WebRequest request, DefaultSessionAttributeStore store, ModelMap model) throws ServletException, IOException {
        clearAttributes(request, store, model);
        ModelAndView modelAndView = new ModelAndView("manager/orders/addOrder");
        return modelAndView;
    }

    @RequestMapping("/orders/add-load-point")
    protected String addLoadPoint(WebRequest request, DefaultSessionAttributeStore store) {
        try {
            RoutePoint routePoint = parseRoutePoint(request);
            List<RoutePoint> route = (List<RoutePoint>)store.retrieveAttribute(request, "route");
            if (route.size() > 0)
                route.get(route.size() - 1).setNextRoutePoint(routePoint);
            route.add(routePoint);
            store.storeAttribute(request, "route", route);
            List<Cargo> loadedCargos = (List<Cargo>)store.retrieveAttribute(request, "loadedCargos");
            loadedCargos.add(routePoint.getCargo());
            store.storeAttribute(request, "loadedCargos", loadedCargos);
            return "manager/orders/addOrder";
        } catch (CargoValidationException ex) {
            request.setAttribute("cargoValidationException", ex, WebRequest.SCOPE_REQUEST);
            return "manager/orders/addOrder";
        } catch (Exception ex) {
            logger.error(ex.toString());
            request.setAttribute("errorMessage", ex.toString(), WebRequest.SCOPE_REQUEST);
            return "error";
        }
    }

    @RequestMapping("/orders/add-unload-point")
    protected String addUnloadPoint(WebRequest request, DefaultSessionAttributeStore store) {
        List<Cargo> loadedCargos = (List<Cargo>)store.retrieveAttribute(request, "loadedCargos");
        RoutePoint routePoint = parseUnloadRoutePoint(request, loadedCargos);
        List<RoutePoint> route = (List<RoutePoint>)store.retrieveAttribute(request, "route");
        if (route.size() > 0)
            route.get(route.size() - 1).setNextRoutePoint(routePoint);
        route.add(routePoint);
        store.storeAttribute(request, "route", route);
        loadedCargos.remove(routePoint.getCargo());
        store.storeAttribute(request, "loadedCargos", loadedCargos);
        setAddOrderSessionAttributes(request, store);
        return "manager/orders/addOrder";
    }

    @RequestMapping("/orders/get-available-trucks")
    protected String getAvailableTrucks(WebRequest request, DefaultSessionAttributeStore store) {
        Order orderToCreate = (Order) store.retrieveAttribute(request, "order");
        List<RoutePoint> route = (List<RoutePoint>)store.retrieveAttribute(request, "route");
        orderToCreate.setRoute(route.get(0));
        store.storeAttribute(request, "order", orderToCreate);
        store.storeAttribute(request, "isRouteFormed", true);
        List<Truck> suitableTrucks = truckService.getSuitableTrucksForOrder(orderToCreate);
        store.storeAttribute(request, "trucks", suitableTrucks);
        return "manager/orders/addOrder";
    }

    @RequestMapping("/orders/assign-truck")
    protected String assignTruck(WebRequest request, DefaultSessionAttributeStore store) {
        Order orderToCreate = (Order) store.retrieveAttribute(request, "order");
        int truckId = Integer.valueOf(request.getParameter("truckToAssign"));
        Truck truckToAssign = truckService.findById(truckId);
        orderToCreate.setAssignedTruck(truckToAssign);
        store.storeAttribute(request, "order", orderToCreate);
        int deliveryTimeThisMonth = orderService.getDeliveryTimeThisMonth(orderToCreate);
        int deliveryTimeNextMonth = orderService.getDeliveryTimeNextMonth(orderToCreate);
        List<Driver> suitableDrivers = driverService.getSuitableDriversForOrder(orderToCreate, deliveryTimeThisMonth, deliveryTimeNextMonth);
        store.storeAttribute(request, "drivers", suitableDrivers);
        setAddOrderSessionAttributes(request, store);
        return "manager/orders/addOrder";
    }

    @RequestMapping("/orders/assign-driver")
    protected String assignDriver(WebRequest request, DefaultSessionAttributeStore store) {
        Order orderToCreate = (Order)store.retrieveAttribute(request, "order");
        String[] params = request.getParameter("driverToAssign").split(" ");
        int driverId = Integer.valueOf(params[0]);
        int driverIndex = Integer.valueOf(params[1]);
        Driver driver = driverService.findById(driverId);
        if (orderToCreate.getAssignedDrivers() == null)
            orderToCreate.setAssignedDrivers(new ArrayList<Driver>());
        orderToCreate.getAssignedDrivers().add(driver);
        store.storeAttribute(request, "order", orderToCreate);
        driver.setOrder(orderToCreate);
        driver.setCurrentTruck(orderToCreate.getAssignedTruck());
        // it' s better to reget from db cause another client
        // could have assigned these drivers
        List<Driver> suitableDrivers = (List<Driver>)store.retrieveAttribute(request, "drivers");
        suitableDrivers.remove(driverIndex);
        store.storeAttribute(request, "drivers", suitableDrivers);
        setAddOrderSessionAttributes(request, store);
        return "manager/orders/addOrder";
    }

    @RequestMapping("/orders/create")
    protected String createOrder(WebRequest request, DefaultSessionAttributeStore store, ModelMap model) {
        Order orderToCreate = (Order)store.retrieveAttribute(request, "order");
        try {
            orderService.create(orderToCreate);
        } catch (UnloadNotLoadedCargoException |
                NotAllCargosUnloadedException | DoubleLoadCargoException ex) {
            logger.error(ex.toString());
            request.setAttribute("errorMessage", ex.toString(), WebRequest.SCOPE_REQUEST);
            return "error";
        } catch (OrderNotCreated ex) {
            logger.error(ex.toString());
            request.setAttribute("errorMessage", ex.getMessage(), WebRequest.SCOPE_REQUEST);
            return "error";
        }
        clearAttributes(request, store, model);
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

    private void clearAttributes(WebRequest request, DefaultSessionAttributeStore store, ModelMap model) {
        model.remove("route");
        store.cleanupAttribute(request, "route");
        model.remove("order");
        store.cleanupAttribute(request, "order");
        model.remove("drivers");
        store.cleanupAttribute(request, "drivers");
        model.remove("trucks");
        store.cleanupAttribute(request, "trucks");
        model.remove("loadedCargos");
        store.cleanupAttribute(request, "loadedCargos");
        model.remove("isRouteFormed");
        store.cleanupAttribute(request, "isRouteFormed");
        model.remove("isTruckAssigned");
        store.cleanupAttribute(request, "isTruckAssigned");
        model.remove("isShiftFormed");
        store.cleanupAttribute(request, "isShiftFormed");
        model.remove("isValidRoute");
        store.cleanupAttribute(request, "isValidRoute");

        store.storeAttribute(request, "cities", cityService.findAll());
        store.storeAttribute(request, "route", new ArrayList<RoutePoint>());
        store.storeAttribute(request, "order", new Order());
        store.storeAttribute(request, "drivers", new ArrayList<Driver>());
        store.storeAttribute(request, "trucks", new ArrayList<Truck>());
        store.storeAttribute(request, "loadedCargos", new ArrayList<Cargo>());
        store.storeAttribute(request, "isRouteFormed", false);
        store.storeAttribute(request, "isTruckAssigned", false);
        store.storeAttribute(request, "isShiftFormed", false);
        store.storeAttribute(request, "isValidRoute", false);
    }

    private void setAddOrderSessionAttributes(WebRequest request, DefaultSessionAttributeStore store) {
        List<RoutePoint> route = (List<RoutePoint>)store.retrieveAttribute(request, "route");
        Boolean isValidRoute = isValidRoute(route);
        store.storeAttribute(request, "isValidRoute", isValidRoute.toString());
        Order orderToCreate = (Order)store.retrieveAttribute(request, "order");
        if (orderToCreate != null) {
            if (orderToCreate.getAssignedTruck() != null) {
                store.storeAttribute(request, "isTruckAssigned", true);
            }
            if (((Boolean)store.retrieveAttribute(request, "isTruckAssigned")).equals(true))
                if (orderToCreate.getAssignedDrivers() != null)
                    if (orderToCreate.getAssignedDrivers().size() == orderToCreate.getAssignedTruck().getShiftSize()) {
                        store.storeAttribute(request, "isShiftFormed", true);
                    }
        }
    }

    private RoutePoint parseRoutePoint(WebRequest request) throws CargoValidationException {
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

    private RoutePoint parseUnloadRoutePoint(WebRequest request, List<Cargo> loadedCargos) {
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
