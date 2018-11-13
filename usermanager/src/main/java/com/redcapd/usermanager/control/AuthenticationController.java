package com.redcapd.usermanager.control;

import com.redcapd.usermanager.UserManagerDao;
import com.redcapd.usermanager.entity.User;
import org.jose4j.jwe.JsonWebEncryption;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@ApplicationScoped
public class AuthenticationController {
    @Inject
    UserManagerDao userManagerDao;


    public String authenticateUser(String username, String password){
        User user = userManagerDao.getUserByUsername(username);
        return user.authenticate(password);
    }
}
