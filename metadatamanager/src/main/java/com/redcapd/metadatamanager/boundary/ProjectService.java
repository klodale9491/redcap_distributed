package com.redcapd.metadatamanager.boundary;

import com.redcapd.metadatamanager.entity.ProjectEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("project")
public class ProjectService {
    @PersistenceContext(unitName = "MyPersistenceUnit")
    private EntityManager entityManager;
    @Inject
    private UserData userData;

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Secured
    public Response createProject(ProjectEntity p){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(p);
        entityManager.getTransaction().commit();
        return Response.status(200).build();
    }

    @GET
    @Produces("application/json")
    @Secured
    public Response getProjects(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        List projects = entityManager.createNativeQuery("SELECT id,name,description FROM project INNER JOIN project_user ON project.id = project_user.project_id WHERE project_user.user_id  = :uid")
                .setParameter("uid",this.userData.getUserId()).getResultList();
        entityManager.getTransaction().commit();
        return Response.status(200).entity(projects).build();
    }

    @GET
    @Produces("application/json")
    @Path("{pid}")
    @Secured
    public Response getProjectById(@PathParam("pid") long pid){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        entityManager = emf.createEntityManager();
        ProjectEntity proj = entityManager.find(ProjectEntity.class,pid);
        return Response.status(200).entity(proj).build();
    }

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Secured
    public Response updateProject(ProjectEntity project){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(project);// se metto id fa UPDATE, altrimenti da INSERT
        entityManager.getTransaction().commit();
        return Response.status(200).build();
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    @Secured
    public Response deleteProject(@PathParam("id") long id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPersistenceUnit");
        entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        ProjectEntity project = entityManager.find(ProjectEntity.class,id);
        entityManager.remove(project); // fa DELETE
        entityManager.getTransaction().commit();
        return Response.status(200).build();
    }
}
