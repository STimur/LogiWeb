package com.tsystems.javaschool.timber.logiweb.controller;

import com.tsystems.javaschool.timber.logiweb.dao.jpa.CityDaoJpa;
import com.tsystems.javaschool.timber.logiweb.dao.jpa.DriverDaoJpa;
import com.tsystems.javaschool.timber.logiweb.dao.jpa.OrderDaoJpa;
import com.tsystems.javaschool.timber.logiweb.entity.City;
import com.tsystems.javaschool.timber.logiweb.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.entity.DriverState;
import com.tsystems.javaschool.timber.logiweb.entity.Order;
import com.tsystems.javaschool.timber.logiweb.exceptions.DoubleLoadCargoException;
import com.tsystems.javaschool.timber.logiweb.exceptions.NotAllCargosUnloadedException;
import com.tsystems.javaschool.timber.logiweb.exceptions.UnloadNotLoadedCargoException;
import com.tsystems.javaschool.timber.logiweb.service.CityService;
import com.tsystems.javaschool.timber.logiweb.service.DriverService;
import com.tsystems.javaschool.timber.logiweb.service.OrderService;
import com.tsystems.javaschool.timber.logiweb.service.impl.CityServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.impl.DriverServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.impl.OrderServiceImpl;

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
public class OrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
                case "create":
                    Order order = parseOrder(request);
                    try {
                        orderService.create(order);
                    } catch (UnloadNotLoadedCargoException e) {
                        e.printStackTrace();
                    } catch (NotAllCargosUnloadedException e) {
                        e.printStackTrace();
                    } catch (DoubleLoadCargoException e) {
                        e.printStackTrace();
                    }
                    break;
                case "list":
                    break;
                case "stateList":
                    List<Order> orders = orderService.findAll();
                    request.setAttribute("orders", orders);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/orders/ordersState.jsp");
                    rd.forward(request, response);
                    break;
                case "delete":
                    id = parseOrderId(request);
                    orderService.delete(id);
                    break;
            }
        }

        List<Order> orders = orderService.findAll();
        request.setAttribute("orders", orders);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/orders/orders.jsp");
        rd.forward(request, response);
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

}
