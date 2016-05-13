package com.base.knowledge.dao.hibernate;

import com.base.knowledge.domain.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HibernateUserDaoTest {

    @Mock
    SessionFactory sessionFactory;
    @Mock
    Session session;
    @Mock
    Query query;

    HibernateUserDao hibernateUserDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        hibernateUserDao = new HibernateUserDao();
        hibernateUserDao.setSessionFactory(this.sessionFactory);
    }

    @After
    public void tearDown(){
        verifyNoMoreInteractions(sessionFactory, session);
    }

    @Test
    public void testGetUserById() throws Exception {
        //arrange
        User user = new User("Name", "LastName", "Login", "Password");
        User userResult;
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.get(User.class, user.getId())).thenReturn(user);
        //act
        userResult = hibernateUserDao.getUserById(user.getId());
        //assert
        assertEquals(userResult.getName(), "Name");
        assertSame(user, userResult);
        verify(sessionFactory, times(1)).openSession();
        verify(session, times(1)).get(User.class, user.getId());
        verify(session, times(1)).isOpen();
    }

    @Test
    public void testGetUserById_Without_SessionFactory() throws Exception {
        //arrange
        User user = new User("Name", "LastName", "Login", "Password");
        User userResult;
        when(sessionFactory.openSession()).thenReturn(null);
        //act
        userResult = hibernateUserDao.getUserById(user.getId());
        //assert
        assertNull(userResult);
        assertNotSame(user, userResult);
        verify(sessionFactory, times(1)).openSession();
    }

    @Test
    public void testGetUserByLoginAndPassword() throws Exception {
        //arrange
        User user = new User("Name", "LastName", "Login", "Password");
        User userResult;
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.createQuery("FROM User WHERE login =:login and password =:password")).thenReturn(query);
        when(query.setParameter("login", user.getLogin())).thenReturn(query);
        when(query.setParameter("password", user.getPassword())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(user);
        //act
        userResult = hibernateUserDao.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
        //assert
        assertEquals(userResult.getName(), "Name");
        assertEquals(userResult.getLogin(), "Login");
        assertEquals(userResult.getPassword(), "Password");
        assertEquals(userResult.getLastName(), "LastName");
        assertSame(user, userResult);
        verify(sessionFactory, times(1)).openSession();
        verify(session, times(1)).createQuery("FROM User WHERE login =:login and password =:password");
        verify(query, times(1)).setParameter("login", user.getLogin());
        verify(query, times(1)).setParameter("password", user.getPassword());
        verify(query, times(1)).uniqueResult();
        verify(session, times(1)).isOpen();
        verifyNoMoreInteractions(query);
    }

    @Test
    public void testGetUserByLoginAndPassword_Without_SessionFactory() throws Exception {
        //arrange
        User user = new User("Name", "LastName", "Login", "Password");
        when(sessionFactory.openSession()).thenReturn(null);
        //act
        User userResult = hibernateUserDao.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
        //assert
        assertNull(userResult);
        assertNotSame(user, userResult);
        verify(sessionFactory, times(1)).openSession();
        verifyNoMoreInteractions(query);
    }

    @Test
    public void testPutUser() throws Exception {
        //arrange
        User user = new User("Name", "LastName", "Login", "Password");
        user.setId(10);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.save(user)).thenReturn(user.getId());
        //act
        hibernateUserDao.putUser(user);
        //assert
        assertEquals(session.save(user), 10);
        verify(sessionFactory, times(1)).openSession();
        verify(session, times(2)).save(user);
        verify(session, times(1)).isOpen();
    }

    @Test
    public void testPutUser_With_Null_Question() throws Exception {
        //arrange
        User user = null;
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.save(user)).thenReturn(null);
        //act
        hibernateUserDao.putUser(user);
        //assert
        assertEquals(session.save(user), null);
        verify(sessionFactory, times(1)).openSession();
        verify(session, times(2)).save(user);
        verify(session, times(1)).isOpen();
    }

    @Test
    public void testDeleteUser() throws Exception {
        //arrange
        User user = new User(1, "Name", "LastName", "Login", "Password");
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.get(User.class, user.getId())).thenReturn(user);
        //act
        hibernateUserDao.deleteUser(user);
        //assert
        verify(sessionFactory, times(1)).openSession();
        verify(session ,times(1)).delete(user);
        verify(session, times(1)).get(User.class, user.getId());
        verify(session, times(1)).flush();
        verify(session, times(1)).isOpen();
        verifyNoMoreInteractions(query);
    }

    @Test
    public void testDeleteUser_Without_SessionFactory() throws Exception {
        //arrange
        User user = new User(1, "Name", "LastName", "Login", "Password");
        when(sessionFactory.openSession()).thenReturn(null);
        //act
        hibernateUserDao.deleteUser(user);
        //assert
        verify(sessionFactory, times(1)).openSession();
        verifyNoMoreInteractions(query);
    }
}