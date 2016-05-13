package com.base.knowledge.dao.hibernate;

import com.base.knowledge.domain.Question;
import com.base.knowledge.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.hibernate.Query;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HibernateQuestionDaoTest {

    @Mock
    SessionFactory sessionFactory;
    @Mock
    Session session;
    @Mock
    Query query;

    HibernateQuestionDao hibernateQuestionDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        hibernateQuestionDao = new HibernateQuestionDao();
        hibernateQuestionDao.setSessionFactory(this.sessionFactory);
    }

    @After
    public void tearDown(){
        verifyNoMoreInteractions(sessionFactory, session);
    }

    @Test
    public void testGetQuestionById() throws Exception {
        //arrange
        Question question = new Question("Who?");
        Question questionResult;
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.get(Question.class, question.getId())).thenReturn(question);
        //act
        questionResult = hibernateQuestionDao.getQuestionById(question.getId());
        //assert
        assertEquals(questionResult.getContent(), "Who?");
        assertSame(question, questionResult);
        verify(sessionFactory, times(1)).openSession();
        verify(session, times(1)).get(Question.class, question.getId());
        verify(session, times(1)).isOpen();
    }

    @Test
    public void testGetQuestionById_Without_SessionFactory() throws Exception {
        //arrange
        Question question = new Question("Who?");
        Question questionResult;
        when(sessionFactory.openSession()).thenReturn(null);
        //act
        questionResult = hibernateQuestionDao.getQuestionById(question.getId());
        //assert
        assertNull(questionResult);
        assertNotSame(question, questionResult);
        verify(sessionFactory, times(1)).openSession();
    }

    @Test
    public void testGetAllQuestionByUser() throws Exception {
        //arrange
        User user =  new User(1, "name", "lastName", "login", "password");
        List<Question> currentList = new LinkedList<>();
        currentList.add(new Question());
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.createQuery("FROM Question WHERE owner = :owner")).thenReturn(query);
        when(query.setParameter("owner", user)).thenReturn(query);
        when(query.list()).thenReturn(currentList);
        //act
        List<Question> resultList = hibernateQuestionDao.getAllQuestionByUser(user);
        //assert
        assertEquals(resultList.size(), 1);
        assertSame(currentList, resultList);
        verify(sessionFactory, times(1)).openSession();
        verify(session, times(1)).createQuery("FROM Question WHERE owner = :owner");
        verify(session, times(1)).isOpen();
        verify(query, times(1)).setParameter("owner", user);
        verify(query, times(1)).list();
        verifyNoMoreInteractions(query);
    }

    @Test
    public void testGetAllQuestionBy_NullUser() throws Exception {
        //arrange
        User user =  null;
        List<Question> currentList = new LinkedList<>();
        currentList.add(new Question());
        when(sessionFactory.openSession()).thenReturn(session);
        //act
        List<Question> resultList = hibernateQuestionDao.getAllQuestionByUser(user);
        //assert
        assertNull(resultList);
        assertNotSame(resultList, currentList);
        verify(sessionFactory, times(1)).openSession();
        verify(session, times(1)).isOpen();
        verifyNoMoreInteractions(query);
    }

    @Test
    public void testGetAllQuestionByUser_Without_SessionFactory() throws Exception {
        //arrange
        User user =  new User(1, "name", "lastName", "login", "password");
        List<Question> currentList = new LinkedList<>();
        currentList.add(new Question());
        when(sessionFactory.openSession()).thenReturn(null);
        //act
        List<Question> resultList = hibernateQuestionDao.getAllQuestionByUser(user);
        //assert
        assertNull(resultList);
        assertNotSame(resultList, currentList);
        verify(sessionFactory, times(1)).openSession();
        verifyNoMoreInteractions(query);
    }

    @Test
    public void testPutQuestion() throws Exception {
        //arrange
        Question question = new Question("Why?");
        question.setId(10);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.save(question)).thenReturn(question.getId());
        //act
        hibernateQuestionDao.putQuestion(question);
        //assert
        assertEquals(session.save(question), 10);
        verify(sessionFactory, times(1)).openSession();
        verify(session, times(2)).save(question);
        verify(session, times(1)).isOpen();
    }

    @Test
    public void testPutQuestion_With_Null_Question() throws Exception {
        //arrange
        Question question = null;
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.save(question)).thenReturn(null);
        //act
        hibernateQuestionDao.putQuestion(question);
        //assert
        assertEquals(session.save(question), null);
        verify(sessionFactory, times(1)).openSession();
        verify(session, times(2)).save(question);
        verify(session, times(1)).isOpen();
    }

    @Test
    public void testDeleteQuestionById() throws Exception {
        //arrange
        Question question = new Question("Why?");
        question.setId(10);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.createQuery("delete Question where id = :ID")).thenReturn(query);
        when(query.setParameter("ID", question.getId())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);
        //act
        hibernateQuestionDao.deleteQuestionById(question.getId());
        //assert
        assertEquals(query.executeUpdate(), 1);
        verify(sessionFactory, times(1)).openSession();
        verify(session, times(1)).createQuery("delete Question where id = :ID");
        verify(query, times(1)).setParameter("ID", question.getId());
        verify(query, times(2)).executeUpdate();
        verify(session, times(1)).isOpen();
        verifyNoMoreInteractions(query);
    }

    @Test
    public void testDeleteQuestionById_Without_SessionFactory() throws Exception {
        //arrange
        Question question = new Question("Why?");
        question.setId(10);
        when(sessionFactory.openSession()).thenReturn(null);
        //act
        hibernateQuestionDao.deleteQuestionById(question.getId());
        //assert
        verify(sessionFactory, times(1)).openSession();
        verifyNoMoreInteractions(query);
    }
}