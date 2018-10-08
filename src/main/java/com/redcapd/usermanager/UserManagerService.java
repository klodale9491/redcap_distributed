package com.redcapd.usermanager;
import com.redcapd.usermanager.entity.UserEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("usrmng")
public class UserManagerService {
    @Inject
    private UserManagerDao userManagerDao;


    @POST
    @Path("add")
    @Produces("text/html")
    public Response addUser(@FormParam("usr") String usr,
                            @FormParam("psw") String psw,
                            @FormParam("eml") String email,
                            @FormParam("lng") int lang){
        String responseString = (userManagerDao.createUser(usr,psw,email,lang) > 0) ? "OK" : "BAD";
        return Response.status(200).entity(responseString).build();
    }


    @GET
    @Path("get")
    @Produces("application/json")
    public Response getUser(@QueryParam("id") int id){
        List<UserEntity> users = userManagerDao.getAllUsers();
        JSONArray jsonArray = new JSONArray();
        try{
            for(UserEntity usr : users){
                JSONObject obj = new JSONObject();
                obj.put("id",usr.getId());
                obj.put("username",usr.getUsername());
                obj.put("password",usr.getPassword());
                obj.put("salt",usr.getSalt());
                obj.put("email",usr.getEmail());
                obj.put("language_id",usr.getLanguageId());
                jsonArray.put(obj);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        this.userManagerDao.testScope();
        return Response.status(200).entity(""+this.userManagerDao.getI()).build();
    }

}