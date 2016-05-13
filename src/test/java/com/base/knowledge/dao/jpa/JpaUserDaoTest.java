package com.base.knowledge.dao.jpa;

import com.base.knowledge.domain.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JpaUserDaoTest {

    @Mock
    EntityManager manager;
    @Mock
    Query query;

    JpaUserDao userDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userDao = new JpaUserDao();
        userDao.setEntityManager(this.manager);
    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(manager, query);
    }

    @Test
    public void testGetUserById() throws Exception {
        //arrange
        User user = new User(1, "user", "user", "user", "user");
        when(manager.find(User.class, user.getId())).thenReturn(user);
        //act
        User result = userDao.getUserById(user.getId());
        //assert
        assertSame(result, user);
        assertEquals(result.getId(), user.getId());
        assertEquals(result.getName(), user.getName());
        assertEquals(result.getLastName(), user.getLastName());
        assertEquals(result.getLogin(), user.getLogin());
        assertEquals(result.getPassword(), result.getPassword());
        verify(manager, times(1)).find(User.class, user.getId());
    }

    @Test
    public void testGetUserById_WithoutManager() throws Exception {
        //arrange
        User user = new User(1, "user", "user", "user", "user");
        when(manager.find(User.class, user.getId())).thenThrow(new NullPointerException());
        //act
        User result = userDao.getUserById(user.getId());
        //assert
        assertNotSame(result, user);
        verify(manager, times(1)).find(User.class, user.getId());
    }

    @Test
    public void testGetUserByLoginAndPassword() throws Exception {
        //arrange
        User user = new User(1, "user", "user", "user", "user");
        when(manager.createQuery("FROM User where login =:login and password =:password")).thenReturn(query);
        when(query.setParameter("login", user.getLogin())).thenReturn(query);
        when(query.setParameter("password", user.getPassword())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(user);
        //act
        User result = userDao.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
        //assert
        assertSame(result, user);
        assertEquals(result.getId(), user.getId());
        assertEquals(result.getName(), user.getName());
        assertEquals(result.getLastName(), user.getLastName());
        assertEquals(result.getLogin(), user.getLogin());
        assertEquals(result.getPassword(), result.getPassword());
        verify(manager, times(1)).createQuery("FROM User where login =:login and password =:password");
        verify(query, times(1)).setParameter("login", user.getLogin());
        verify(query, times(1)).setParameter("password", user.getPassword());
        verify(query, times(1)).getSingleResult();
    }

    @Test
    public void testGetUserByLoginAndPassword_WithoutManager() throws Exception {
        //arrange
        User user = new User(1, "user", "user", "user", "user");
        when(manager.createQuery("FROM User where login =:login and password =:password")).thenThrow(new NullPointerException());
        //act
        User result = userDao.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
        //assert
        assertNotSame(user, result);
        verify(manager, times(1)).createQuery("FROM User where login =:login and password =:password");
    }

    @Test
    public void testPutUser() throws Exception {
        //arrange
        User user = new User(1, "user", "user", "user", "user");
        //act
        userDao.putUser(user);
        //assert
        verify(manager, times(1)).persist(user);
    }

    @Test
    public void testPutUser_WithoutManeger() throws Exception {
        //arrange
        User user = new User(1, "user", "user", "user", "user");
        doThrow(new NullPointerException()).when(manager).persist(user);
        //act
        userDao.putUser(user);
        //assert
        verify(manager, times(1)).persist(user);
    }

    @Test
    public void testDeleteUser() throws Exception {
        //arrange
        User user = new User(1, "user", "user", "user", "user");
        when(manager.find(User.class, user.getId())).thenReturn(user);
        //act
        userDao.deleteUser(user);
        //assert
        verify(manager, times(1)).find(User.class, user.getId());
        verify(manager, times(1)).remove(user);
    }

    @Test
    public void testDeleteUser_WithoutManger() throws Exception {
        //arrange
        User user = new User(1, "user", "user", "user", "user");
        doThrow(new NullPointerException()).when(manager).remove(user);
        when(manager.find(User.class, user.getId())).thenReturn(user);
        //act
        userDao.deleteUser(user);
        //assert
        verify(manager, times(1)).remove(user);
        verify(manager, times(1)).find(User.class, user.getId());
    }
}