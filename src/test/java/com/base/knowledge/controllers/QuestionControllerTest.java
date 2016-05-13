package com.base.knowledge.controllers;

import com.base.knowledge.domain.Question;
import com.base.knowledge.domain.User;
import com.base.knowledge.services.interfaces.QuestionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class QuestionControllerTest {

    @Mock
    HttpServletRequest request;
    @Mock
    QuestionService questionService;
    @Mock
    HttpSession session;
    @Mock
    RequestDispatcher requestDispatcher;

    QuestionController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new QuestionController();
        controller.setQuestionService(this.questionService);
    }

    @After
    public void tearDown(){
        verifyNoMoreInteractions(request, session, requestDispatcher, questionService);
    }

    @Test
    public void testMakeQuestion_No_Parameters_And_No_User() throws Exception {
        //arrange
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getParameter("content")).thenReturn(null);
        //act
        String result = controller.makeQuestion(request, session);
        //assert
        assertEquals(result, "main");
        verify(session, times(1)).getAttribute("user");
        verify(request, times(1)).getParameter("content");
    }

    @Test
    public void testMakeQuestion_One_Parameter() throws Exception {
        //arrange
        User user = new User("Name", "LastName", "Login", "Password");
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("content")).thenReturn(null);
        //act
        String result = controller.makeQuestion(request, session);
        //assert
        assertEquals(result, "main");
        assertSame(session.getAttribute("user"), user);
        verify(session, times(2)).getAttribute("user");
        verify(request, times(1)).getParameter("content");
    }

    @Test
    public void testMakeQuestion_With_Parameter() throws Exception {
        //arrange
        User user = new User(1, "Name", "LastName", "Login", "Password");
        String content = "content";
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("content")).thenReturn(content);
        //act
        String result = controller.makeQuestion(request, session);
        //assert
        assertEquals(result, "main");
        assertSame(session.getAttribute("user"), user);
        verify(questionService, times(1)).putQuestion((Question)anyObject());
        verify(session, times(2)).getAttribute("user");
        verify(request, times(1)).getParameter("content");
    }
}