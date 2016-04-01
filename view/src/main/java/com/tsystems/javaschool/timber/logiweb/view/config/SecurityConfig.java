package com.tsystems.javaschool.timber.logiweb.view.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("manager").password("manager").roles("manager");
        auth.inMemoryAuthentication().withUser("driver").password("driver").roles("driver");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.antMatchers("/driver/find/").permitAll()
                .antMatchers("/", "/home").access("hasRole('manager') or hasRole('driver')")
                .antMatchers("/drivers*").access("hasRole('manager')")
                .antMatchers("/trucks*").access("hasRole('manager')")
                .antMatchers("/orders*").access("hasRole('manager')")
                .antMatchers("/cargos*").access("hasRole('manager')")
                .antMatchers("/drivers/get-job-info").access("hasRole('driver')")
                .antMatchers("/drivers/job-info").access("hasRole('driver')")
                .and().formLogin().loginPage("/login")
                .usernameParameter("username").passwordParameter("password")
                .and().exceptionHandling().accessDeniedPage("/accessDenied")
                .and().csrf().disable();
        ;
    }
}
