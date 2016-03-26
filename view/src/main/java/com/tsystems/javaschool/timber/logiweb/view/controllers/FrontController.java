package com.tsystems.javaschool.timber.logiweb.view.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
public class FrontController {
    String message = "Welcome to Spring MVC!";

    @RequestMapping(value = {"/", "/home"})
    public ModelAndView sayHello() {
        System.out.println("Hi, this is Spring MVC! =)");
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("message", message);
        return mv;
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        this.logout();
        return "home";
    }
}
