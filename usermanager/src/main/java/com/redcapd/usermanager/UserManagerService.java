package com.redcapd.usermanager;


import com.redcapd.usermanager.control.AuthenticationController;
import com.redcapd.usermanager.entity.UserEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("users")
public class UserManagerService {
    @Inject
    private UserManagerDao userManagerDao;
    @Inject
    private AuthenticationController authenticationController;


    @POST
    @Path("auth")
    @Produces("text/html")
    public Response authUser(@FormParam("usr") String usr,
                             @FormParam("psw") String psw) {
        try{
            String jsonToken = authenticationController.authenticateUser(usr,psw);
            // Stacco il token ...
            return Response.status(200).entity(jsonToken).build();

        }
        catch(EntityNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
    //
    // Crea un utente.
    //
    @POST
    @Produces("text/html")
    public Response addUser(@FormParam("usr") String usr,
                            @FormParam("psw") String psw,
                            @FormParam("eml") String email,
                            @FormParam("lng") int lang){
        int responseCode = (userManagerDao.createUser(usr,psw,email,lang) == 0) ? 201 : 400;
        return Response.status(responseCode).entity("").build();
    }


    //
    // Aggiorna un utente.
    //
    @PUT
    @Path("{id}")
    @Produces("text/html")
    public Response updateUser(
                            @PathParam("id") @Pattern(regexp = "[1-9]+", message = "Errore parametro ID") int id,
                            @FormParam("usr") String usr,
                            @FormParam("psw") String psw,
                            @FormParam("eml") String email,
                            @FormParam("lng") int lang){
        String responseString = (userManagerDao.updateUser(id,usr,psw,email,lang) == 0) ? "OK" : "BAD";
        return Response.status(200).entity(responseString).build();
    }


    //
    // Cancella un utente.
    //
    @DELETE
    @Path("{id}")
    @Produces("text/html")
    public Response updateUser(
            @PathParam("id") int id){
        String responseString = (userManagerDao.deleteUser(id) == 0) ? "OK" : "BAD";
        return Response.status(204).entity(responseString).build();
    }

    //
    // Carica un singolo utente.
    //
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getUser(@PathParam("id") int id){
        UserEntity usr = null;
        try{
            usr = userManagerDao.getUser(id);
            JSONObject obj = new JSONObject();
            obj.put("id",usr.getId());
            obj.put("username",usr.getUsername());
            obj.put("password",usr.getPassword());
            obj.put("salt",usr.getSalt());
            obj.put("email",usr.getEmail());
            obj.put("language_id",usr.getLanguageId());
            return Response.status(200).entity(obj.toString()).build();
        }
        catch(EntityNotFoundException e){
            return Response.status(200).entity("").build();
        }
    }

    //
    // Carica tutti gli utenti.
    //
    @GET
    @Produces("application/json")
    public Response getAllUser(){
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
        return Response.status(200).entity(jsonArray.toString()).build();
    }

}