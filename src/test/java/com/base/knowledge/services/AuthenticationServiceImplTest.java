package com.base.knowledge.services;

import com.base.knowledge.dao.UserDao;
import com.base.knowledge.domain.User;
import com.base.knowledge.services.implementations.AuthenticationServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AuthenticationServiceImplTest {

    @Mock
    UserDao userDao;

    AuthenticationServiceImpl service;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        service = new AuthenticationServiceImpl();
        service.setUserDao(this.userDao);
    }

    @After
    public void tearDown(){
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void testCheckAndReturnUser_No_Parameters() throws Exception {
        //arrange
        User user = new User("Name", "LastName", "Login", "Password");
        //act
        User resultUser = service.checkAndReturnUser(null, null);
        //assert
        assertNull(resultUser);
        assertNotSame(resultUser, user);
    }

    @Test
    public void testCheckAndReturnUser_With_Parameters() throws Exception {
        //arrange
        User user = new User("Name", "LastName", "Login", "Password");
        when(userDao.getUserByLoginAndPassword(user.getLogin(), user.getPassword())).thenReturn(user);
        //act
        User resultUser = service.checkAndReturnUser(user.getLogin(), user.getPassword());
        //assert
        assertNotNull(resultUser);
        assertSame(resultUser, user);
        verify(userDao, times(1)).getUserByLoginAndPassword(user.getLogin(), user.getPassword());
    }

}