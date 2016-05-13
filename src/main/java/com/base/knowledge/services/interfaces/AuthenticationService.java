package com.base.knowledge.services.interfaces;


import com.base.knowledge.domain.User;

public interface AuthenticationService {

    public User checkAndReturnUser(String userLogin, String userPassword);

}
