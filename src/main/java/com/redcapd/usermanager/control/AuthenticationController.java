package com.redcapd.usermanager.control;

import com.redcapd.usermanager.UserManagerDao;
import com.redcapd.usermanager.entity.User;
import org.jose4j.jwe.JsonWebEncryption;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;


@SessionScoped
public class AuthenticationController {
    @Inject
    UserManagerDao userManagerDao;


    public JsonWebEncryption authenticateUser(String username, String password){
        User user = userManagerDao.getUserByUsername(username);

    }
}
