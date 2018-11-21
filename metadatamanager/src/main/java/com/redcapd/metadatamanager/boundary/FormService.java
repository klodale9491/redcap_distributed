package com.redcapd.metadatamanager.boundary;

import com.redcapd.metadatamanager.entity.FormEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("forms")
public class FormService {
    private EntityManager em;

    @GET
    @Produces("application/json")
    public Response getFormsByProject(@QueryParam("pid") long pid) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
        List forms = em.createNativeQuery("SELECT id,name FROM form WHERE form.project_id = :pid").setParameter("pid", pid).getResultList();
        return Response.status(200).entity(forms).build();
    }

    @GET
    @Path("{fid}")
    @Produces("application/json")
    public Response getFormById(@PathParam("fid") long fid){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
        //List forms = em.createNativeQuery("SELECT id,name FROM form WHERE id = :fid").setParameter("fid", fid).getResultList();
        FormEntity form = em.find(FormEntity.class,fid);
        return Response.status(200).entity(form).build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response creteForm(FormEntity form){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(form); // fa INSERT
        em.getTransaction().commit();
        return Response.status(200).build();
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Response deleteForm(@PathParam("id") long id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        FormEntity form = em.find(FormEntity.class,id);
        em.remove(form); // fa DELETE
        em.getTransaction().commit();
        return Response.status(200).build();
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateForm(FormEntity form){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(form);// se metto id fa UPDATE, altrimenti da INSERT
        em.getTransaction().commit();
        return Response.status(200).build();
    }
}
