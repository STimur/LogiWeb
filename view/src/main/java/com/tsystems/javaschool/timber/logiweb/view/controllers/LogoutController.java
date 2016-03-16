package com.tsystems.javaschool.timber.logiweb.view.controllers;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class LogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    final static Logger logger = Logger.getLogger(LogoutController.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getUserPrincipal().getName();
        String userrole = "";
        if (request.isUserInRole("manager"))
            userrole = "manager";
        else
            userrole = "driver";
        logger.info("User: " + username + " in role: " + userrole + " logged out at " + new Date());
        request.getSession().invalidate();
        response.sendRedirect(getServletContext().getContextPath() + "/home.jsp");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
