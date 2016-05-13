package com.base.knowledge.dao.jdbc;

import com.base.knowledge.domain.Question;
import com.base.knowledge.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JdbcQuestionDaoTest {

    @Mock
    private DataSource dataSource;
    @Mock
    Connection connection;
    @Mock
    ResultSet resultSet;
    @Mock
    PreparedStatement preparedStatement;

    JdbcQuestionDao jdbcQuestionDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        jdbcQuestionDao = new JdbcQuestionDao();
        jdbcQuestionDao.setDataSource(this.dataSource);
    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(connection, preparedStatement, resultSet, dataSource);
    }

    @Test
    public void testGetQuestionById() throws Exception {
        //arrange
        User owner = new User("Login");
        Question question = new Question("Why?");
        question.setId(10);
        question.setOwner(owner);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.getCatalog()).thenReturn("way");
        when(connection.prepareStatement("SELECT * FROM way.question WHERE id = ?")).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(question.getId());
        when(resultSet.getString("content")).thenReturn(question.getContent());
        when(resultSet.getObject("owner")).thenReturn(question.getOwner());
        //act
        Question resultQuestion = jdbcQuestionDao.getQuestionById(question.getId());
        //assert
        assertEquals(resultQuestion.getContent(), question.getContent());
        assertEquals(resultQuestion.getId(), question.getId());
        assertEquals(resultQuestion.getOwner(), question.getOwner());
        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).getCatalog();
        verify(connection, times(1)).prepareStatement("SELECT * FROM way.question WHERE id = ?");
        verify(connection, times(1)).close();
        verify(preparedStatement, times(1)).setInt(1, question.getId());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getInt("id");
        verify(resultSet, times(1)).getObject("owner");
        verify(resultSet, times(1)).getString("content");
    }

    @Test
    public void testGetAllQuestionByUser() throws Exception {
        //arrange
        User owner = new User(1, "Name", "LastName", "Login", "Password");
        Question question = new Question("Why?");
        question.setId(1);
        question.setOwner(owner);
        List<Question> questionList = new LinkedList<>();
        questionList.add(question);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.getCatalog()).thenReturn("way");
        when(connection.prepareStatement("SELECT * FROM way.question WHERE owner = ?")).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(question.getId());
        when(resultSet.getString("content")).thenReturn(question.getContent());
        when(resultSet.getObject("owner")).thenReturn(question.getOwner());
        //act
        List<Question> resultList = jdbcQuestionDao.getAllQuestionByUser(owner);
        //assert
        assertEquals(questionList.size(), resultList.size());
        assertEquals(questionList.get(0).getOwner(), resultList.get(0).getOwner());
        assertEquals(questionList.get(0).getId(), resultList.get(0).getId());
        assertEquals(questionList.get(0).getContent(), resultList.get(0).getContent());
        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).getCatalog();
        verify(connection, times(1)).prepareStatement("SELECT * FROM way.question WHERE owner = ?");
        verify(connection, times(1)).close();
        verify(preparedStatement, times(1)).setObject(1, owner);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(1)).getInt("id");
        verify(resultSet, times(1)).getObject("owner");
        verify(resultSet, times(1)).getString("content");
    }

    @Test
    public void testPutQuestion() throws Exception {
        //arrange
        User owner = new User(1, "Name", "LastName", "Login", "Password");
        Question question = new Question("Why?");
        question.setId(1);
        question.setOwner(owner);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.getCatalog()).thenReturn("way");
        when(connection.prepareStatement("INSERT INTO way.question (content, owner) VALUES (?, ?)")).thenReturn(preparedStatement);
        when(connection.prepareStatement("SELECT id FROM way.question WHERE owner = ?")).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(question.getId());
        //act
        jdbcQuestionDao.putQuestion(question);
        //assert
        verify(dataSource, times(2)).getConnection();
        verify(connection, times(2)).getCatalog();
        verify(connection, times(1)).prepareStatement("INSERT INTO way.question (content, owner) VALUES (?, ?)");
        verify(connection, times(1)).prepareStatement("SELECT id FROM way.question WHERE owner = ?");
        verify(connection, times(2)).close();
        verify(preparedStatement, times(1)).setString(1, question.getContent());
        verify(preparedStatement, times(1)).setObject(2, question.getOwner());
        verify(preparedStatement, times(1)).setObject(1, question.getOwner());
        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getInt("id");
    }

    @Test
    public void testDeleteQuestionById() throws Exception {
        //arrange
        User owner = new User(1, "Name", "LastName", "Login", "Password");
        Question question = new Question("Why?");
        question.setId(1);
        question.setOwner(owner);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.getCatalog()).thenReturn("way");
        when(connection.prepareStatement("DELETE FROM way.question WHERE id = ?")).thenReturn(preparedStatement);
        //act
        jdbcQuestionDao.deleteQuestionById(question.getId());
        //assert
        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).getCatalog();
        verify(connection, times(1)).prepareStatement("DELETE FROM way.question WHERE id = ?");
        verify(connection, times(1)).close();
        verify(preparedStatement, times(1)).setInt(1, question.getId());
        verify(preparedStatement, times(1)).executeUpdate();
    }
}