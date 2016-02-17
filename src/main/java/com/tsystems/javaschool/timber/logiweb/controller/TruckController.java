package com.tsystems.javaschool.timber.logiweb.controller;

import com.tsystems.javaschool.timber.logiweb.dao.TruckDao;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.service.TruckService;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Test
 */
public class TruckController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TruckController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String id_param = request.getParameter("id");
        int id = -1;
        if (id_param != null)
            id = Integer.valueOf(request.getParameter("id"));

        TruckService truckService = new TruckService(new TruckDao());

        if (action != null) {
            switch (action) {
                case "create":
                    Truck newTruck = new Truck();
                    newTruck.setRegNumber("XXXXXXX");
                    newTruck.setShiftSize(1);
                    newTruck.setCapacity(10);
                    newTruck.setState("OK");
                    truckService.create(newTruck);
                    break;
                case "delete":
                    truckService.delete(id);
                    break;
                case "edit":
                    Truck truckToEdit = truckService.findById(id);
                    String state = truckToEdit.getState();
                    if (state.compareTo("OK")==0) truckToEdit.setState("BROKEN");
                    else truckToEdit.setState("OK");
                    truckService.update(truckToEdit);
            }
        }

        List<Truck> trucks = truckService.findAll();
        request.setAttribute("trucks", trucks);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/trucks.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
