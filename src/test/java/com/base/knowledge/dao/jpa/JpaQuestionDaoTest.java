package com.base.knowledge.dao.jpa;

import com.base.knowledge.domain.Question;
import com.base.knowledge.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JpaQuestionDaoTest {

    @Mock
    EntityManager manager;
    @Mock
    Query query;

    JpaQuestionDao questionDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        questionDao = new JpaQuestionDao();
        questionDao.setEntityManager(this.manager);
    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(manager, query);
    }

    @Test
    public void testGetQuestionById() throws Exception {
        //arrange
        User owner = new User("Login");
        Question question = new Question("Why?", owner);
        question.setId(1);
        when(manager.find(Question.class, question.getId())).thenReturn(question);
        //act
        Question result = questionDao.getQuestionById(question.getId());
        //assert
        assertSame(result, question);
        assertEquals(question.getId(), result.getId());
        assertEquals(result.getContent(), "Why?");
        assertSame(result.getOwner(), question.getOwner());
        verify(manager, times(1)).find(Question.class, question.getId());
    }

    @Test
    public void testGetQuestionById_WithoutManager() throws Exception {
        //arrange
        User owner = new User("Login");
        Question question = new Question("Why?", owner);
        question.setId(1);
        when(manager.find(Question.class, question.getId())).thenThrow(new NullPointerException());
        //act
        Question result = questionDao.getQuestionById(question.getId());
        //assert
        assertNull(result);
        assertNotSame(result, question);
        verify(manager, times(1)).find(Question.class, question.getId());
    }

    @Test
    public void testGetAllQuestionByUser() throws Exception {
        //arrange
        User owner = new User(10, "admin", "admin", "admin", "admin");
        List<Question> list = new LinkedList<>();
        list.add(new Question("why?", owner));
        when(manager.createQuery("FROM Question WHERE owner = :owner")).thenReturn(query);
        when(query.setParameter("owner", owner)).thenReturn(query);
        when(query.getResultList()).thenReturn(list);
        //act
        List<Question> result = questionDao.getAllQuestionByUser(owner);
        //assert
        assertSame(result, list);
        assertEquals(result.size(), list.size());
        verify(manager, times(1)).createQuery("FROM Question WHERE owner = :owner");
        verify(query, times(1)).setParameter("owner", owner);
        verify(query, times(1)).getResultList();
    }

    @Test
    public void testPutQuestion() throws Exception {
        //arrange
        User owner = new User(10, "admin", "admin", "admin", "admin");
        Question question = new Question("why?", owner);
        //act
        questionDao.putQuestion(question);
        //assert
        verify(manager, times(1)).persist(question);
    }

    @Test
    public void testDeleteQuestionById() throws Exception {
        //arrange
        User owner = new User(10, "admin", "admin", "admin", "admin");
        Question question = new Question("why?", owner);
        when(manager.find(Question.class, question.getId())).thenReturn(question);
        //act
        questionDao.deleteQuestionById(question.getId());
        //assert
        verify(manager, times(1)).find(Question.class, question.getId());
        verify(manager, times(1)).remove(question);
    }
}