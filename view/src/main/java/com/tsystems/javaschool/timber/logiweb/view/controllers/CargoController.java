package com.tsystems.javaschool.timber.logiweb.view.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.CargoDaoJpa;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Cargo;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.CargoService;
import com.tsystems.javaschool.timber.logiweb.service.impl.CargoServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.util.Services;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class CargoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargoController() {
        super();
        // TODO Auto-generated constructor stub
    }

    @RequestMapping("/cargos-state")
    protected ModelAndView getCargosState() throws ServletException, IOException {
        List<Cargo> cargos = Services.getCargoService().findAll();
        ModelAndView mv = new ModelAndView("manager/cargos/cargosState");
        mv.addObject("cargos", cargos);
        return mv;
        /*String action = request.getParameter("action");
        List<Cargo> cargos = Services.getCargoService().findAll();
        RequestDispatcher requestDispatcher;

        if (action != null) {
            switch (action) {
                case "list":
                    request.setAttribute("cargos", cargos);
                    requestDispatcher = getServletContext().getRequestDispatcher("/jsp/manager/cargos/cargos.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                case "stateList":
                    request.setAttribute("cargos", cargos);
                    requestDispatcher = getServletContext().getRequestDispatcher("/jsp/manager/cargos/cargosState.jsp");
                    requestDispatcher.forward(request, response);
                    break;
            }
        }*/
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
