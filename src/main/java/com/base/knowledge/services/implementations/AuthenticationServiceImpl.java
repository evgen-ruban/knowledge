package com.base.knowledge.services.implementations;


import com.base.knowledge.dao.UserDao;
import com.base.knowledge.domain.User;
import com.base.knowledge.services.interfaces.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    @Qualifier("jpaUserDao")
    private UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    public User checkAndReturnUser(String userLogin, String userPassword){
        User user = null;
        LOGGER.trace("checking login and password");
        if (userLogin != null && userPassword != null) {
            user = userDao.getUserByLoginAndPassword(userLogin, userPassword);
        }
        if(user == null){
            LOGGER.info("USER failed authentication");
        }
        return user;
    }
}
