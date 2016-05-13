package com.base.knowledge.dao;


import com.base.knowledge.domain.User;
import com.base.knowledge.exception.KnowledgeException;

import java.sql.SQLException;

public interface UserDao {

    public User getUserById(int id);

    public User getUserByLoginAndPassword(String login, String password);

    public void putUser(User user) throws KnowledgeException;

    public void deleteUser(User user);

}
