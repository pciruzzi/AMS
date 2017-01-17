package fr.insa.ams.ws;

import fr.insa.ams.*;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import fr.insa.ams.json.ApplicationAdapter;
import fr.insa.ams.json.ApplicationStateAdapter;
import fr.insa.ams.json.CVAdapter;
import fr.insa.ams.json.StudentAdapter;
import fr.insa.ams.stateMachine.ApplicationEvent;
import fr.insa.ams.stateMachine.ApplicationState;
import fr.insa.ams.stateMachine.StateMachine;
import java.net.URI;
import java.util.List;

import javax.ws.rs.DELETE;
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
        // It only can be called by the applications' owner
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
//         if (! applications.isEmpty() && applications.get(0).getPartner().getId() != userId) return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(Application.class, new ApplicationAdapter()).create();
        return Response.ok(gson.toJson(applications), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/offers/{id}/students")
    @Produces("application/json")
    public Response getStudentsForOffer(@HeaderParam("id") int userId, @PathParam("id") int offerId) {
        Database db = new Database();
        List<Student> students = db.getStudentsByOffer(offerId);
        // TODO: It only can be called by the offer owner??
//         if (! applications.isEmpty() && applications.get(0).getPartner().getId() != userId) return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(Student.class, new StudentAdapter()).registerTypeAdapter(CV.class, new CVAdapter()).create();
        return Response.ok(gson.toJson(students), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}/state")
    @Produces("application/json")
    public Response getState(@HeaderParam("id") int userId, @PathParam("id") int appId) {
        Database db = new Database();
        Application application = db.getApplication(appId);
        if (application == null) return Response.status(Response.Status.NOT_FOUND).build();
        if (userId != application.getStudent().getId() &&
            userId != application.getPartner().getId() &&
            userId != application.getCoordinator().getId() &&
            userId != db.getFSD().getId()) //TODO: Administrator?
                return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        ApplicationState state = application.getState();
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
        if (application == null) return Response.status(Response.Status.NOT_FOUND).build();
        if (userId != application.getStudent().getId() &&
            userId != application.getPartner().getId() &&
            userId != application.getCoordinator().getId() &&
            userId != db.getFSD().getId()) //TODO: Administrator?
                return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        ApplicationState state = application.getState();
        ApplicationEvent event = db.getActor(userId).getApplicationEvent(accept);
        state = StateMachine.makeApplicationTransition(state, event);
        application.setState(state);
        db.update(application);
        GsonBuilder gsonBuilder = new GsonBuilder();
        if (state == ApplicationState.ACCEPTED) {
            InternshipAgreement agreement = new InternshipAgreement(application);
            db.add(agreement);
        }
        Gson gson = gsonBuilder.registerTypeAdapter(ApplicationState.class, new ApplicationStateAdapter()).create();
        return Response.ok(gson.toJson(state), MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteApplication(@HeaderParam("id") int userId, @PathParam("id") int appId) {
        Database db = new Database();
        // Only the administrator can delete an application
        if (db.getAdministrator().getId() != userId) return Response.status(Response.Status.UNAUTHORIZED).build();
        Application application = db.getApplication(appId);
        if (application == null) return Response.status(Response.Status.NOT_FOUND).build();
        db.delete(application);
        return Response.ok().build();
    }

    @POST
    public Response addApplication(@HeaderParam("id") int userId, @QueryParam("studentID") int studentID,
                                                     @QueryParam("partnerID") int partnerID, @QueryParam("offerID") int offerID,
                                                     @QueryParam("cvID") int cvId, String coverLetter) {
        // Only the student can apply
        if (userId != studentID) return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        Database db = new Database();

        Student student;
        try {
            student = (Student) db.getActor(studentID);
        } catch (ClassCastException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (student == null) return Response.status(Response.Status.BAD_REQUEST).build();

        if (! db.existsCoordinator(student.getYear(), student.getPathway())) return Response.status(Response.Status.CONFLICT).build();
        ClassCoordinator coordinator = db.getCoordinator(student.getYear(), student.getPathway());

        Partner partner = null;
        try {
            partner = (Partner) db.getActor(partnerID);
        } catch (ClassCastException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (partner == null) return Response.status(Response.Status.BAD_REQUEST).build();

        if (db.existsApplication(studentID, partnerID, offerID)) return Response.status(Response.Status.NOT_MODIFIED).build();

        CV cv = null;
        if (cvId != -1) cv = db.getCV(cvId); //In order to use -1 when testing

        Application application = new Application(student, coordinator, partner, offerID, cv, coverLetter);
        int applicationId = db.add(application);
        return Response.created(URI.create(String.valueOf(applicationId))).build();
    }
}
