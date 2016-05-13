package com.base.knowledge.dao.jdbc;

import com.base.knowledge.dao.UserDao;
import com.base.knowledge.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class JdbcUserDao implements UserDao {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcUserDao.class);

    @Deprecated
    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        return null;
    }

    @Override
    public User getUserById(int id) {
        LOGGER.trace("Open jdbc connection");
        Connection connection = null;
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
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + catalog +".user" + " WHERE id = ?";
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            LOGGER.warn("SQLException in getUserById in class: " + JdbcUserDao.class, e);
        }
        try {
            if (!resultSet.next()) {
                LOGGER.trace("DataBase does not have user with id = " + id);
                user = null;
            } else {
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setId(resultSet.getInt("id"));
                LOGGER.trace("DataBase have user with id = " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            LOGGER.trace("CLOSE jdbc connection");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public void putUser(User user) {
        LOGGER.trace("Open jdbc connection");
        Connection connection = null;
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
        String query = "INSERT INTO " + catalog + ".user" + " (name, lastname, login, password) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("SQLException in putUser in class: " + JdbcUserDao.class, e);
        }
        setUserId(user);
        try {
            LOGGER.trace("CLOSE jdbc connection");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteUser(User user) {
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
        String query = "DELETE FROM " + catalog + ".user" + " WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("SQLException in deleteUserById in class: " + JdbcUserDao.class);
            e.printStackTrace();
        }
        try {
            LOGGER.trace("CLOSE jdbc connection");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setUserId(User user) {
        Connection connection = null;
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
        String query = "SELECT id FROM " + catalog + ".user" + " WHERE login = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user.setId(resultSet.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
