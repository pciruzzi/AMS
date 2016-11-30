package fr.insa.ams.ws;

import fr.insa.ams.*;
import com.google.gson.Gson;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
    public String getApplications(@HeaderParam("id") int userId, @QueryParam("id") int id) {
        Database db = new Database();
        List<Application> applications = db.getApplications(id);
        String result = "[\n";
        for (Application app : applications) {
            result = result.concat("\t{\n");
            result = result.concat("\t\t\"id\": " + app.getId() + ",\n");
            result = result.concat("\t\t\"idStudent\": " + app.getStudent().getId() + ",\n");
            result = result.concat("\t\t\"idPartner\": " + app.getPartner().getId() + ",\n");
            result = result.concat("\t\t\"idCoodrinator\": " + app.getCoordinator().getId() + ",\n");
            result = result.concat("\t\t\"idOffer\": " + app.getOfferID() + "\n");
            result = result.concat("\t},\n");
        }
        int ind = result.lastIndexOf(',');
        if (ind >= 0) result = new StringBuilder(result).replace(ind, ind+1, "").toString();
        result = result.concat("]\n");
        return result;
//        return new Gson().toJson(applications);
    }

    @GET
    @Path("/state/{id}")
    @Produces("text/plain")
    public Response getState(@HeaderParam("id") int userId, @PathParam("id") int id) {
        if (userId != id) return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        Database db = new Database();
        ApplicationState state = db.getApplicationState(id);
        return Response.ok(state.getMessage(), MediaType.TEXT_PLAIN).build();
    }

    // TODO: It should only receive studentID and offerID, the coordinator should be able to look for it in the DB with
    //            the year and pathway of the student, and the partner with the offerID??
    @PUT
    @Consumes("application/json")
    public void addApplication(@HeaderParam("id") int userId, @QueryParam("studentID") int studentID, @QueryParam("coordinatorID") int coordinatorID,
                                             @QueryParam("partnerID") int partnerID, @QueryParam("offerID") int offerID) {
        Database db = new Database();
        Actor student = db.getActor(studentID);
        Actor coordinator = db.getActor(coordinatorID);
        Actor partner = db.getActor(partnerID);
        Application application = new Application(student, coordinator, partner, offerID);
        db.add(application);
    }
}
