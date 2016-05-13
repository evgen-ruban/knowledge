package com.base.knowledge.services.implementations;


import com.base.knowledge.dao.UserDao;
import com.base.knowledge.domain.User;
import com.base.knowledge.exception.KnowledgeException;
import com.base.knowledge.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("jpaUserDao")
    private UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }


    @Override
    public void putUser(User user) throws KnowledgeException{
        userDao.putUser(user);
    }

    @Override
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

}
