package com.redhat;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.json.JSONException;

@Path("/hello")
public class TrackerResource {

	@Inject
	CovidService service;
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{state}")
    public String daily(@PathParam String state) throws JSONException, IOException {
        return service.daily(state);
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/historical/{state}")
    public String historical(@PathParam String state) throws JSONException, IOException {
        return service.history(state);
    }
}
