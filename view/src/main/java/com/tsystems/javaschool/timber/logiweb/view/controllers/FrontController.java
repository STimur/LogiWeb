package com.tsystems.javaschool.timber.logiweb.view.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FrontController {
    String message = "Welcome to Spring MVC!";

    @RequestMapping("/home")
    public ModelAndView sayHello() {
        System.out.println("Hi, this is Spring MVC! =)");
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("message", message);
        return mv;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "login";
    }
}
