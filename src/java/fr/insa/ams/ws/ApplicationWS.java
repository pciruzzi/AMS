package fr.insa.ams.ws;

import fr.insa.ams.*;
import com.google.gson.Gson;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("applications")
public class ApplicationWS {
    @Context
    private UriInfo context;

    public ApplicationWS() {
    }

    @GET
    @Produces("application/json")
    public Response getApplications(@HeaderParam("id") int userId, @QueryParam("id") int id) {
        if (userId != id) return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        Database db = new Database();
        List<Application> applications = db.getApplications(id);
        String result = "[\n";
        for (Application app : applications) {
            result = result.concat("\t{\n");
            result = result.concat("\t\t\"id\": " + app.getId() + ",\n");
            result = result.concat("\t\t\"idStudent\": " + app.getStudent().getId() + ",\n");
            result = result.concat("\t\t\"idPartner\": " + app.getPartner().getId() + ",\n");
            result = result.concat("\t\t\"idCoordinator\": " + app.getCoordinator().getId() + ",\n");
            result = result.concat("\t\t\"idOffer\": " + app.getOfferID() + "\n");
            result = result.concat("\t},\n");
        }
        int ind = result.lastIndexOf(',');
        if (ind >= 0) result = new StringBuilder(result).replace(ind, ind+1, "").toString();
        result = result.concat("]\n");
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
        // TODO: Gson not working with nested Hibernate objects...
//        return new Gson().toJson(applications);
    }

    @GET
    @Path("/{id}/state")
    @Produces("text/plain")
    public Response getState(@HeaderParam("id") int userId, @PathParam("id") int id) {
        Database db = new Database();
        Application application = db.getApplication(id);
        if (userId != application.getStudent().getId() &&
            userId != application.getPartner().getId() &&
            userId != application.getCoordinator().getId())
                return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        ApplicationState state = db.getApplicationState(id);
        return Response.ok(state.getMessage(), MediaType.TEXT_PLAIN).build();
    }

    // TODO: It should only receive studentID and offerID, the coordinator should be able to look for it in the DB with
    //            the year and pathway of the student, and the partner with the offerID??
    @POST
    public Response addApplication(@HeaderParam("id") int userId, @QueryParam("studentID") int studentID, @QueryParam("coordinatorID") int coordinatorID,
                                                     @QueryParam("partnerID") int partnerID, @QueryParam("offerID") int offerID) {
        if (userId != studentID) return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        Database db = new Database();
        Actor student = db.getActor(studentID);
        Actor coordinator = db.getActor(coordinatorID);
        Actor partner = db.getActor(partnerID);
        Application application = new Application(student, coordinator, partner, offerID);
        int applicationId = db.add(application);
        return Response.created(URI.create(String.valueOf(applicationId))).build();
    }
}
