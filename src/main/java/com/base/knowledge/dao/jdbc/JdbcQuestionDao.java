package com.base.knowledge.dao.jdbc;

import com.base.knowledge.dao.QuestionDao;
import com.base.knowledge.domain.Question;
import com.base.knowledge.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;


@Repository
public class JdbcQuestionDao implements QuestionDao {


    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcQuestionDao.class);

    @Override
    public Question getQuestionById(int id) {
        Question question = new Question();
            try{
                Connection connection = dataSource.getConnection();
                LOGGER.trace("Open jdbc connection");
                String catalog = connection.getCatalog();
                String query = "SELECT * FROM " + catalog + ".question" + " WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    LOGGER.trace("no one Question");
                    question = null;
                } else {
                    question.setId(resultSet.getInt("id"));
                    question.setContent(resultSet.getString("content"));
                    question.setOwner((User)resultSet.getObject("owner"));
                    LOGGER.trace("Question is valid");
                }
                connection.close();
                LOGGER.trace("CLOSE jdbc connection");
            } catch (SQLException e){
                LOGGER.warn("SQLException in getQuestionById in class: " + JdbcQuestionDao.class, e);
            }
        return question;
    }

    @Override
    public List<Question> getAllQuestionByUser(User user) {
        List<Question> list = null;
        try {
            Connection connection = dataSource.getConnection();
            LOGGER.trace("Open jdbc connection");
            String catalog = connection.getCatalog();
            String query = "SELECT * FROM " + catalog + ".question" + " WHERE owner = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                LOGGER.trace("USER does not have Questions");
                list = null;
            } else {
                LOGGER.trace("USER has Questions");
                list = new LinkedList<>();
                while (resultSet.next()) {
                    Question question = new Question(resultSet.getString("content"), (User)resultSet.getObject("owner"));
                    question.setId(resultSet.getInt("id"));
                    list.add(question);
                }
            }
            connection.close();
            LOGGER.trace("CLOSE jdbc connection");
        } catch (SQLException e) {
            LOGGER.warn("SQLException in getAllQuestionByUser in class: " + JdbcQuestionDao.class, e);
        }
        return list;
    }

    @Override
    public void putQuestion(Question question) {
        Connection connection = null;
        LOGGER.trace("Open jdbc connection");
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String catalog = "";
        try {
            catalog = connection.getCatalog();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query = "INSERT INTO " + catalog + ".question" + " (content, owner) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, question.getContent());
            preparedStatement.setObject(2, question.getOwner());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("SQLException in putQuestion in class: " + JdbcQuestionDao.class);
            e.printStackTrace();
        }
        setQuestionId(question);
        try {
            LOGGER.trace("CLOSE jdbc connection");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteQuestionById(int id) {
        Connection connection = null;
        LOGGER.trace("Open jdbc connection");
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String catalog = "";
        try {
            catalog = connection.getCatalog();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query = "DELETE FROM " + catalog + ".question" + " WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("SQLException in deleteQuestionById in class: " + JdbcQuestionDao.class);
            e.printStackTrace();
        }
        try {
            LOGGER.trace("CLOSE jdbc connection");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setQuestionId(Question question) {
        Connection connection = null;
        LOGGER.trace("Open jdbc connection");
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String catalog = "";
        try {
            catalog = connection.getCatalog();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query = "SELECT id FROM " + catalog + ".question" + " WHERE owner = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, question.getOwner());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                question.setId(resultSet.getInt("id"));
            }

        } catch (SQLException e) {
            LOGGER.warn("SQLException in getAllQuestionByUser in class: " + JdbcQuestionDao.class);
            e.printStackTrace();
        }
        try {
            LOGGER.trace("CLOSE jdbc connection");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
