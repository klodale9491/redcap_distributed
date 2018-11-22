package com.redcapd.metadatamanager.boundary;

import com.redcapd.metadatamanager.entity.FieldEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.List;


@Path("forms/{fid}/fields/")
public class FieldService {
    private EntityManager em;
    @Inject
    private UserData userData;
    @PathParam("fid") long fid;

    @GET
    @Produces("application/json")
    @Secured
    public Response getAllFormFields(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
        List fields = em.createNativeQuery("SELECT field.* FROM field INNER JOIN form ON field.form_id = form.id INNER JOIN project_user ON project_user.project_id = form.project_id WHERE field.form_id = :fid AND project_user.user_id = :uid",FieldEntity.class)
                .setParameter("fid", fid)
                .setParameter("uid", userData.getUserId())
                .getResultList();
        return Response.status(200).entity(fields).build();
    }

    @GET
    @Produces("application/json")
    @Path("{ffid}")
    @Secured
    public Response getFormFieldById(@PathParam("ffid") long id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
        FieldEntity field = (FieldEntity) em.createNativeQuery("SELECT field.* FROM field INNER JOIN form ON field.form_id = form.id INNER JOIN project_user ON project_user.project_id = form.project_id WHERE field.id = :id AND project_user.user_id = :uid",FieldEntity.class)
                .setParameter("id", id)
                .setParameter("uid", userData.getUserId())
                .getSingleResult();
        return Response.status(200).entity(field).build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Secured
    public Response creteFormField(FieldEntity field){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
        if (isUserAuthorized()) {
            em.getTransaction().begin();
            em.persist(field); // fa INSERT
            em.getTransaction().commit();
            return Response.status(Response.Status.CREATED).build();
        } else
            return Response.status(Response.Status.UNAUTHORIZED).build();
    }


    @DELETE
    @Path("{ffid}")
    @Produces("application/json")
    @Secured
    public Response deleteFormField(@PathParam("ffid") long ffid){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        if (isUserAuthorized()) {
            FieldEntity field = em.find(FieldEntity.class,ffid);
            em.remove(field); // fa DELETE
            em.getTransaction().commit();
            return Response.status(Response.Status.OK).build();
        } else
            return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Secured
    public Response updateField(FieldEntity field){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
       if (isUserAuthorized()) {
           em.getTransaction().begin();
           em.merge(field);// se metto id fa UPDATE, altrimenti da INSERT
           em.getTransaction().commit();
           return Response.status(Response.Status.OK).build();
       } else
           return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    private boolean isUserAuthorized() {
        BigInteger c = (BigInteger) em.createNativeQuery("SELECT COUNT(*) FROM form INNER JOIN project_user ON form.project_id = project_user.project_id WHERE project_user.user_id = :uid and form.id = :fid")
                .setParameter("uid", userData.getUserId())
                .setParameter("fid", fid)
                .getSingleResult();
        return c.compareTo(new BigInteger("0")) > 0;
    }
}