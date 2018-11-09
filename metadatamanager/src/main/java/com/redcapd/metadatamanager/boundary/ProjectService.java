package com.redcapd.metadatamanager.boundary;


import com.redcapd.metadatamanager.entity.ProjectEntity;
import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("project")
public class ProjectService {
    @PersistenceContext(unitName = "MyPersistenceUnit")
    EntityManager entityManager;

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response createProject(ProjectEntity p){
        try{
            entityManager.persist(p);
        }
        catch(HibernateException e){
            e.printStackTrace();
            return Response.status(500).build();
        }
        return Response.status(200).entity(p).build();
    }

    @GET
    @Produces("application/json")
    @Path("{pid}")
    public Response getProjectById(@PathParam("pid") long pid){
        return null;
    }

    @GET
    @Produces("application/json")
    public Response getProjects(){
        return null;
    }

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response updateProject(ProjectEntity p){
        return null;
    }
}
