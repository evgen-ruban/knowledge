package com.base.knowledge.controllers;

import com.base.knowledge.domain.User;
import com.base.knowledge.exception.KnowledgeException;
import com.base.knowledge.services.interfaces.UserService;
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
public class RegisterController {

    @Autowired
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public ModelAndView registration(HttpServletRequest request, ModelAndView modelAndView, HttpSession session) {
        LOGGER.trace("Start registration");
        User newUser = null;

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String login = request.getParameter("userLogin");
        String password = request.getParameter("userPassword");

        LOGGER.trace("user wrote his data = " + "firstName: " + firstName + " lastName: " + lastName +
        " login: " + login + " password: " + password);

        if(firstName == null || lastName == null || login == null || password == null) {
            LOGGER.info("SOME from data is NULL");
            modelAndView = new ModelAndView("register");
            LOGGER.trace("return to register");
            return modelAndView;
        }

        newUser = new User(firstName, lastName, login, password);

        try {
            userService.putUser(newUser);
        } catch (KnowledgeException e) {
            LOGGER.info("User with this login already exists");
            return new ModelAndView("register", "message", "User with this login already exists");
        }
        modelAndView = new ModelAndView("main", "user", newUser);
        session.setAttribute("user", newUser);
        LOGGER.info("User successfully registered!!!");
        return modelAndView;
    }
}
