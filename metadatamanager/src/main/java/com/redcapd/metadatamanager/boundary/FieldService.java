package com.redcapd.metadatamanager.boundary;

import com.redcapd.metadatamanager.entity.FieldEntity;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("forms/{fid}/fields/")
public class FieldService {

    @GET
    @Produces("application/json")
    public Response getAllFormFields(@PathParam("fid") long id){

    }

    @GET
    @Produces("application/json")
    @Path("{ffid}")
    public Response getFormFieldById(@PathParam("ffid") long id){

    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response creteFormField(FieldEntity field){

    }

    @DELETE
    @Produces("application/json")
    public Response deleteAllFormField(){

    }

    @DELETE
    @Path("{ffid}")
    @Produces("application/json")
    public Response deleteFormField(@PathParam("ffid") long id){

    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateForm(FieldEntity field){

    }
}
