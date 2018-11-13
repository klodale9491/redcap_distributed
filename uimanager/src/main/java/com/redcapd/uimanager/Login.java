package com.redcapd.uimanager;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;

@SessionScoped
@Named("loginBean")
public class Login implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    public String auth() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:9080/usermanager/api/users/auth");
        Form form = new Form();
        form.param("usr", this.username);
        form.param("psw", this.password);
        // da settare in sessione
        String token = target.request().post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
        return (!token.equals("")) ? "home" : "login";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
