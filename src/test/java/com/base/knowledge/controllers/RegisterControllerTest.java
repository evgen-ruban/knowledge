package com.base.knowledge.controllers;

import com.base.knowledge.domain.User;
import com.base.knowledge.services.interfaces.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RegisterControllerTest {

    @Mock
    HttpServletRequest request;
    @Mock
    UserService userService;
    @Mock
    HttpSession session;
    @Mock
    ModelAndView modelAndView;

    RegisterController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new RegisterController();
        controller.setUserService(this.userService);
    }

    @After
    public void tearDown(){
        verifyNoMoreInteractions(request, session, modelAndView);
    }

    @Test
    public void testRegistration_No_Parameters_For_Registration() throws Exception {
        //arrange
        when(request.getParameter("firstName")).thenReturn(null);
        when(request.getParameter("lastName")).thenReturn(null);
        when(request.getParameter("userLogin")).thenReturn(null);
        when(request.getParameter("userPassword")).thenReturn(null);
        //act
        String result = controller.registration(request, modelAndView, session).getViewName();
        //assert
        assertEquals(result, "register");
        verify(request, times(1)).getParameter("firstName");
        verify(request, times(1)).getParameter("lastName");
        verify(request, times(1)).getParameter("userLogin");
        verify(request, times(1)).getParameter("userPassword");
    }

    @Test
    public void testRegistration_With_Parameters() throws Exception {
        //arrange
        when(request.getParameter("firstName")).thenReturn("firstName");
        when(request.getParameter("lastName")).thenReturn("lastName");
        when(request.getParameter("userLogin")).thenReturn("userLogin");
        when(request.getParameter("userPassword")).thenReturn("userPassword");
        //act
        String result = controller.registration(request, modelAndView, session).getViewName();
        //assert
        assertEquals(result, "main");
        verify(request, times(1)).getParameter("firstName");
        verify(request, times(1)).getParameter("lastName");
        verify(request, times(1)).getParameter("userLogin");
        verify(request, times(1)).getParameter("userPassword");
        verify(session, times(1)).setAttribute(eq("user"), isA(User.class));
        verify(userService, times(1)).putUser((User) anyObject());
    }
}