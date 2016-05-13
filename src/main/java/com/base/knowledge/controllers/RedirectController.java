package com.base.knowledge.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RedirectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedirectController.class);

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register () {
        LOGGER.trace("Go to the registration");
        return "register";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String enter() {
        LOGGER.trace("Go to the firstPage.jsp");
        return "firstPage";
    }
}
