package com.base.knowledge.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RedirectControllerTest {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;

    RedirectController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new RedirectController();
    }

    @After
    public void tearDown(){
        verifyNoMoreInteractions(request);
    }

    @Test
    public void testRegister() throws Exception {
        assertEquals("register", controller.register());
    }

    @Test
    public void testEnter() throws Exception {
        assertEquals("firstPage", controller.enter());
    }
}