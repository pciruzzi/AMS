package fr.insa.ams;

import com.google.gson.Gson;

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
    public String getApplications(@QueryParam("id") int id) {
        Database db = new Database();
        List<Application> applications = db.getApplications(id);
        return new Gson().toJson(applications);
    }

    // TODO: It should only receive studentID and offerID, the coordinator should be able to look for it in the DB with
    //            the year and pathway of the student, and the partner with the offerID??
    @PUT
    @Consumes("application/json")
    public void addApplication(@QueryParam("studentID") int studentID, @QueryParam("coordinatorID") int coordinatorID,
                                             @QueryParam("partnerID") int partnerID, @QueryParam("offerID") int offerID) {
        Database db = new Database();
        Actor student = db.getActor(studentID);
        Actor coordinator = db.getActor(coordinatorID);
        Actor partner = db.getActor(partnerID);
        Application application = new Application(student, coordinator, partner, offerID);
        db.add(application);
    }
}
