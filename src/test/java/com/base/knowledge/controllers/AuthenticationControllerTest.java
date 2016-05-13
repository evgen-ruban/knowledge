package com.base.knowledge.controllers;

import com.base.knowledge.domain.User;
import com.base.knowledge.services.interfaces.AuthenticationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;



public class AuthenticationControllerTest {

    @Mock
    HttpServletRequest request;
    @Mock
    AuthenticationService authenticationService;
    @Mock
    HttpSession session;

    AuthenticationController controller;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        controller = new AuthenticationController();
        controller.setAuthenticationService(this.authenticationService);
    }

    @After
    public void tearDown(){
        verifyNoMoreInteractions(request, session, authenticationService);
    }

    @Test
    public void testAuthentication_No_Parameters() throws Exception {
        //arrange
        when(request.getParameter("userLogin")).thenReturn(null);
        when(request.getParameter("userPassword")).thenReturn(null);
        //act
        ModelAndView modelAndView = controller.authentication(request, session);
        //assert
        assertEquals(modelAndView.getViewName(), "firstPage");
        verify(request, times(1)).getParameter("userLogin");
        verify(request, times(1)).getParameter("userPassword");
    }

    @Test
    public void testAuthentication_With_Parameters() throws Exception {
        //arrange
        User user = new User("Name", "LastName", "Login" ,"Password");
        String login = "login";
        String password = "password";
        when(request.getParameter("userLogin")).thenReturn(login);
        when(request.getParameter("userPassword")).thenReturn(password);
        when(authenticationService.checkAndReturnUser(login, password)).thenReturn(user);
        //act
        ModelAndView modelAndView = controller.authentication(request, session);
        //assert
        assertEquals(modelAndView.getViewName(), "main");
        verify(request, times(1)).getParameter("userLogin");
        verify(request, times(1)).getParameter("userPassword");
        verify(session, times(1)).setAttribute("user", user);
        verify(authenticationService, times(1)).checkAndReturnUser(login, password);
    }

    @Test
    public void testLogout() throws Exception {
        //act
        String result = controller.logout(session);
        //assert
        assertEquals(result, "firstPage");
        verify(session, times(1)).invalidate();
    }
}