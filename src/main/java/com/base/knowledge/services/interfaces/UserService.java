package com.base.knowledge.services.interfaces;


import com.base.knowledge.domain.User;
import com.base.knowledge.exception.KnowledgeException;

public interface UserService {

    public User getUserById(int id);

    public void putUser(User user) throws KnowledgeException;

    public void deleteUser(User user);

}
