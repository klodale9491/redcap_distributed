package com.redcapd.metadatamanager.boundary;

import com.redcapd.metadatamanager.entity.FormEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("forms")
public class FormService {
    private EntityManager em;
    @Inject
    private UserData userData;

    @GET
    @Produces("application/json")
    @Secured
    public Response getFormsByProject(@QueryParam("pid") long pid) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
        List forms = em.createNativeQuery("SELECT form.* FROM form INNER JOIN project_user ON project_user.project_id = form.project_id WHERE form.project_id = :pid and project_user.user_id = :uid", FormEntity.class)
                .setParameter("pid", pid)
                .setParameter("uid", this.userData.getUserId())
                .getResultList();
        return Response.status(200).entity(forms).build();
    }

    @GET
    @Path("{fid}")
    @Produces("application/json")
    @Secured
    public Response getFormById(@PathParam("fid") long fid){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
        //List forms = em.createNativeQuery("SELECT id,name FROM form WHERE id = :fid").setParameter("fid", fid).getResultList();
        //FormEntity form = em.find(FormEntity.class,fid);
        try {
            FormEntity form = getFormSecured(fid);
            return Response.status(200).entity(form).build();
        } catch (NoResultException e) {
            return Response.status(404).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Secured
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
    @Secured
    public Response deleteForm(@PathParam("id") long id){
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
            em = emf.createEntityManager();
            em.getTransaction().begin();
            FormEntity form = getFormSecured(id);
            em.remove(form); // fa DELETE
            em.getTransaction().commit();
            return Response.status(200).build();
        } catch (NoResultException e) {
            return Response.status(404).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }

    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Secured
    public Response updateForm(FormEntity form){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(form);// se metto id fa UPDATE, altrimenti da INSERT
        em.getTransaction().commit();
        return Response.status(200).build();
    }

    private FormEntity getFormSecured(long fid) throws Exception {
        return (FormEntity) em.createNativeQuery("select form.* from form INNER JOIN project_user ON form.project_id = project_user.project_id where form.id = :fid and project_user.user_id = :uid", FormEntity.class)
                .setParameter("fid", fid)
                .setParameter("uid", userData.getUserId())
                .getSingleResult();
    }
}
