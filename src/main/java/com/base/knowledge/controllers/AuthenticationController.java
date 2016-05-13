package com.base.knowledge.controllers;

import com.base.knowledge.domain.User;
import com.base.knowledge.services.interfaces.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



@Controller
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    public ModelAndView authentication(HttpServletRequest request, HttpSession session) {
        LOGGER.trace("start authentication user");
        ModelAndView modelAndView = new ModelAndView();
        User user;
        String userLogin = request.getParameter("userLogin");
        String userPassword = request.getParameter("userPassword");

        if(userLogin != null && userPassword != null) {
            LOGGER.trace("login = " + userLogin + ": password = " + userPassword);
            user = authenticationService.checkAndReturnUser(userLogin, userPassword);
        } else {
            LOGGER.info("user failed authentication");
            user = null;
        }

        if (user != null ) {
            modelAndView.addObject("user", user);
            modelAndView.setViewName("main");
            session.setAttribute("user", user);
            LOGGER.trace("set attribute user in Session and in Model");
            LOGGER.trace("REDIRECT to main.jsp");
        } else {
            modelAndView = new ModelAndView("firstPage");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout (HttpSession session) {
        session.invalidate();
        LOGGER.trace("session is invalidated");
        return "firstPage";
    }
}
