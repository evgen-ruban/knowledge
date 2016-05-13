package com.base.knowledge.dao.jdbc;

import com.base.knowledge.domain.User;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JdbcUserDaoTest {

    @Mock
    private DataSource dataSource;
    @Mock
    Connection connection;
    @Mock
    ResultSet resultSet;
    @Mock
    PreparedStatement preparedStatement;

    JdbcUserDao jdbcUserDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        jdbcUserDao = new JdbcUserDao();
        jdbcUserDao.setDataSource(this.dataSource);
    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(connection, preparedStatement, resultSet, dataSource);
    }

    @Test
    public void testGetUserById() throws Exception {
        //arrange
        User user = new User(1, "Name", "LastName", "Login", "Password");
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.getCatalog()).thenReturn("way");
        when(connection.prepareStatement("SELECT * FROM way.user WHERE id = ?")).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("name")).thenReturn(user.getName());
        when(resultSet.getString("lastname")).thenReturn(user.getLastName());
        when(resultSet.getString("login")).thenReturn(user.getLogin());
        when(resultSet.getString("password")).thenReturn(user.getPassword());
        when(resultSet.getInt("id")).thenReturn(user.getId());
        //act
        User resultUser = jdbcUserDao.getUserById(user.getId());
        //assert
        assertEquals(user.getName(), resultUser.getName());
        assertEquals(user.getId(), resultUser.getId());
        assertEquals(user.getLastName(), resultUser.getLastName());
        assertEquals(user.getPassword(), resultUser.getPassword());
        assertEquals(user.getLogin(), resultUser.getLogin());
        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).getCatalog();
        verify(connection, times(1)).prepareStatement("SELECT * FROM way.user WHERE id = ?");
        verify(connection, times(1)).close();
        verify(preparedStatement, times(1)).setInt(1, user.getId());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getString("name");
        verify(resultSet, times(1)).getString("lastname");
        verify(resultSet, times(1)).getString("login");
        verify(resultSet, times(1)).getString("password");
        verify(resultSet, times(1)).getInt("id");
    }

    @Test
    public void testPutUser() throws Exception {
        //arrange
        User user = new User(1, "Name", "LastName", "Login", "Password");
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.getCatalog()).thenReturn("way");
        when(connection.prepareStatement("INSERT INTO way.user (name, lastname, login, password) VALUES (?, ?, ?, ?)")).thenReturn(preparedStatement);
        when(connection.prepareStatement("SELECT id FROM way.user WHERE login = ?")).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(user.getId());
        //act
        jdbcUserDao.putUser(user);
        //assert
        verify(dataSource, times(2)).getConnection();
        verify(connection, times(2)).getCatalog();
        verify(connection, times(1)).prepareStatement("INSERT INTO way.user (name, lastname, login, password) VALUES (?, ?, ?, ?)");
        verify(connection, times(1)).prepareStatement("SELECT id FROM way.user WHERE login = ?");
        verify(connection, times(2)).close();
        verify(preparedStatement, times(1)).setString(3, user.getLogin());
        verify(preparedStatement, times(1)).setString(2, user.getLastName());
        verify(preparedStatement, times(1)).setString(1, user.getName());
        verify(preparedStatement, times(1)).setString(4, user.getPassword());
        verify(preparedStatement, times(1)).setString(1, user.getLogin());
        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getInt("id");
    }

    @Test
    public void testDeleteUser() throws Exception {
        //arrange
        User user = new User(1, "Name", "LastName", "Login", "Password");
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.getCatalog()).thenReturn("way");
        when(connection.prepareStatement("DELETE FROM way.user WHERE id = ?")).thenReturn(preparedStatement);
        //act
        jdbcUserDao.deleteUser(user);
        //assert
        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).getCatalog();
        verify(connection, times(1)).prepareStatement("DELETE FROM way.user WHERE id = ?");
        verify(connection, times(1)).close();
        verify(preparedStatement, times(1)).setInt(1, user.getId());
        verify(preparedStatement, times(1)).executeUpdate();
    }
}