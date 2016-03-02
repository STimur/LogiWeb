package com.tsystems.javaschool.timber.logiweb.view.controllers;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.util.JpaUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by tims on 3/3/2016.
 */
@WebListener
public class ContextController implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        JpaUtil.getEntityManager();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        JpaUtil.closeEntityManagerFactory();
    }
}
