package com.tsystems.javaschool.timber.logiweb.view.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by tims on 2/19/2016.
 */
public class FrontController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //if request is not from HttpServletRequest, you should do a typecast before
        HttpSession session = request.getSession(false);
        //save message in session
        session.setAttribute("username", username);
        session.setAttribute("password", password);
        response.sendRedirect("/home.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
