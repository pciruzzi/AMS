package fr.insa.ams.ws;

import fr.insa.ams.*;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import fr.insa.ams.json.ApplicationAdapter;
import fr.insa.ams.json.ApplicationStateAdapter;
import fr.insa.ams.stateMachine.ApplicationEvent;
import fr.insa.ams.stateMachine.ApplicationState;
import fr.insa.ams.stateMachine.ApplicationStateMachine;
import java.net.URI;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
        List<Application> applications = db.getApplications(userId);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(Application.class, new ApplicationAdapter()).create();
        return Response.ok(gson.toJson(applications), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/offers/{id}")
    @Produces("application/json")
    public Response getApplicationsForOffer(@HeaderParam("id") int userId, @PathParam("id") int offerId) {
        Database db = new Database();
        List<Application> applications = db.getApplicationsByOffer(offerId);
        // TODO: It only can be called by the offer owner??
//         if (applications.get(0).getPartner().getId() != userId) return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(Application.class, new ApplicationAdapter()).create();
        return Response.ok(gson.toJson(applications), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}/state")
    @Produces("application/json")
    public Response getState(@HeaderParam("id") int userId, @PathParam("id") int appId) {
        Database db = new Database();
        Application application = db.getApplication(appId);
        if (userId != application.getStudent().getId() &&
            userId != application.getPartner().getId() &&
            userId != application.getCoordinator().getId())
                return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        ApplicationState state = db.getApplicationState(appId);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(ApplicationState.class, new ApplicationStateAdapter()).create();
        return Response.ok(gson.toJson(state), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    public Response changeState(@HeaderParam("id") int userId, @PathParam("id") int appId, @QueryParam("accept") boolean accept) {
        Database db = new Database();
        Application application = db.getApplication(appId);
        if (userId != application.getStudent().getId() &&
            userId != application.getPartner().getId() &&
            userId != application.getCoordinator().getId() &&
            userId != db.getFSD().getId())
                return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        ApplicationState state = db.getApplicationState(appId);
        ApplicationEvent event = db.getActor(userId).getApplicationEvent(accept);
        state = ApplicationStateMachine.makeTransition(state, event);
        application.setState(state);
        db.updateApplication(application);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(ApplicationState.class, new ApplicationStateAdapter()).create();
        return Response.ok(gson.toJson(state), MediaType.APPLICATION_JSON).build();
    }

    // TODO: It should only receive studentID and offerID, the partner should be able to be found with the offerID??
    @POST
    public Response addApplication(@HeaderParam("id") int userId, @QueryParam("studentID") int studentID,
                                                     @QueryParam("partnerID") int partnerID, @QueryParam("offerID") int offerID) {
        if (userId != studentID) return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        Database db = new Database();
        Student student = (Student) db.getActor(studentID);
        if (! db.existsCoordinator(student.getYear(), student.getPathway())) return Response.status(Response.Status.CONFLICT).build();
        Actor coordinator = db.getCoordinator(student.getYear(), student.getPathway());
        Actor partner = db.getActor(partnerID);
        Application application = new Application(student, coordinator, partner, offerID);
        int applicationId = db.add(application);
        return Response.created(URI.create(String.valueOf(applicationId))).build();
    }
}
