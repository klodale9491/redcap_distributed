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
    @Path("{id}")
    @Produces("application/json")
    public Response getFormById(@PathParam("id") long id){

    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response creteForm(FormEntity form){

    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Response deleteForm(@PathParam("id") long id){

    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateForm(FormEntity form){

    }
}
