package com.redcapd.usermanager;

import com.redcapd.usermanager.entity.UserEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.springframework.security.crypto.bcrypt.BCrypt;


@Path("/usrmng")
public class UserManagerService {

    @GET
    @Path("/get")
    @Produces("text/html")
    public Response getUser(@QueryParam("id") int id){
        Session session = getSession();
        UserEntity user = session.get(UserEntity.class, new Integer(id));
        return (user != null) ? Response.status(200).entity(user).build() : Response.status(200).entity("Nessun utente trovato!").build();
    }

    @POST
    @Path("/add")
    @Produces("text/html")
    public Response addUser(@QueryParam("usr") String usr,
                            @QueryParam("psw") String psw,
                            @QueryParam("eml") String email,
                            @QueryParam("lng") int lang){
        Session session = getSession();
        session.beginTransaction();
        String responseString = "OK";
        try{
            UserEntity user = new UserEntity();
            user.setUsername(usr);
            user.setPassword(psw);
            user.setEmail(email);
            user.setLanguageId(lang);
            // Generazione del salt
            user.setSalt(BCrypt.gensalt());
            session.save(user);
            session.getTransaction().commit();
        }
        catch(HibernateException e){
            session.getTransaction().rollback();
            responseString = "BAD";
        }
        return Response.status(200).entity(responseString).build();
    }

    @GET
    @Path("/sample")
    @Produces("text/html")
    public Response sample2(){
        return Response.status(200).entity("Nessun record trovato!").build();
    }


    private static Session getSession(){
        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        return sessionFactory.openSession();
    }
}
