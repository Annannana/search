package com.cs125.personalaccountant.resources;
import com.cs125.personalaccountant.logger.ServiceLogger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("test")
public class Test {
    @Path("hello")
    @GET
    public Response helloWorld(){
        ServiceLogger.LOGGER.info("Hello!");
        return Response.status(Response.Status.OK).build();
    }
}
