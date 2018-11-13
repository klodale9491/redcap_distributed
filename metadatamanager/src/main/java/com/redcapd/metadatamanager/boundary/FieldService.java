package com.redcapd.metadatamanager.boundary;

import com.redcapd.metadatamanager.entity.FieldEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("forms/{fid}/fields/")
public class FieldService {
    private EntityManager em;

    @GET
    @Produces("application/json")
    public Response getAllFormFields(@PathParam("fid") long fid){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
        List fields = em.createNativeQuery("SELECT label,variable FROM field WHERE field.form_id = :fid").setParameter("fid", fid).getResultList();
        return Response.status(200).entity(fields).build();
    }

    @GET
    @Produces("application/json")
    @Path("{ffid}")
    public Response getFormFieldById(@PathParam("ffid") long ffid){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
        List fields = em.createNativeQuery("SELECT label,variable FROM field WHERE field.id = :ffid").setParameter("ffid", ffid).getResultList();
        return Response.status(200).entity(fields).build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response creteFormField(FieldEntity field){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(field); // fa INSERT
        em.getTransaction().commit();
        return Response.status(200).build();
    }


    @DELETE
    @Path("{ffid}")
    @Produces("application/json")
    public Response deleteFormField(@PathParam("ffid") long ffid){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        FieldEntity field = em.find(FieldEntity.class,ffid);
        em.remove(field); // fa DELETE
        em.getTransaction().commit();
        return Response.status(200).build();
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateField(FieldEntity field){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(field);// se metto id fa UPDATE, altrimenti da INSERT
        em.getTransaction().commit();
        return Response.status(200).build();
    }
}
