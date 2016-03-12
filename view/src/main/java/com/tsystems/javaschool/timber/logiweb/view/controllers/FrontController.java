package com.tsystems.javaschool.timber.logiweb.view.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class FrontController {
    String message = "Welcome to Spring MVC!";

    @RequestMapping("/")
    public ModelAndView sayHello() {
        System.out.println("Hi, this is Spring MVC! =)");

        ModelAndView mv = new ModelAndView("home");
        mv.addObject("message", message);
        return mv;
    }
}
