package fr.insa.ams;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("application")
public class ApplicationWS {
    @Context
    private UriInfo context;

    public ApplicationWS() {
    }

    @GET
    @Produces("application/json")
    public String getApplications(@QueryParam("student") String studentName) {
        List<Application> applications = new ArrayList<Application>();
        Student student = new Student(studentName);
        applications.add(new Application(student, "Airbus"));
        applications.add(new Application(student, "IBM"));
        return  new Gson().toJson(applications);
    }

    @PUT
    @Consumes("application/json")
    public void addApplication(@QueryParam("student") String student, @QueryParam("offer") String offer) {
        // add to list of applications
    }
}
