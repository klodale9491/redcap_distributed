package com.redcapd.metadatamanager.boundary;

import com.redcapd.metadatamanager.control.FormController;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("forms")
public class MetadataManagerService {
    @Inject
    FormController formController;

    @GET
    @Produces("application/json")
    public Response getFormsByProject(@QueryParam("pid") long pid) {

    }
}
