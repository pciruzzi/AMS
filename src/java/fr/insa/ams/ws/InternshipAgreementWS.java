package fr.insa.ams.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.insa.ams.Database;
import fr.insa.ams.InternshipAgreement;
import fr.insa.ams.json.InternshipAgreementAdapter;
import fr.insa.ams.json.InternshipAgreementStateAdapter;
import fr.insa.ams.stateMachine.InternshipAgreementEvent;
import fr.insa.ams.stateMachine.InternshipAgreementState;
import fr.insa.ams.stateMachine.StateMachine;
import java.io.File;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("agreements")
public class InternshipAgreementWS {
    @Context
    private UriInfo context;

    private final String AGREEMENTS_FOLDER = "agreements";

    public InternshipAgreementWS() {
    }

    @GET
    @Produces("application/json")
    public Response getAgreements(@HeaderParam("id") int userId, @QueryParam("id") int id) {
        // It only can be called by the applications' owner
        if (userId != id) return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        Database db = new Database();
        List<InternshipAgreement> agreements = db.getInternshipAgreements(userId);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(InternshipAgreement.class, new InternshipAgreementAdapter()).create();
        return Response.ok(gson.toJson(agreements), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/pdf")
    public Response getAgreementPdf(@HeaderParam("id") int userId, @PathParam("id") int id) {
        Database db = new Database();
        InternshipAgreement agreement = db.getInternshipAgreement(id);
        if (agreement == null) return Response.status(Response.Status.NOT_FOUND).build();
        if (agreement.getState() != InternshipAgreementState.ACCEPTED) return Response.status(Response.Status.CONFLICT).build();
        File file = new File(AGREEMENTS_FOLDER);
        if (! file.exists() && ! file.mkdir()) return Response.status(Response.Status.NOT_MODIFIED).build();
        String filename;
        try {
            filename = agreement.generatePdf(AGREEMENTS_FOLDER);
        } catch (Exception ex) {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
        file = new File(filename);
        return Response.ok(file).header("Content-Disposition", "attachment; filename=\"" + agreement.getId() + ".pdf\"").build();
    }

    @GET
    @Path("/{id}/state")
    @Produces("application/json")
    public Response getState(@HeaderParam("id") int userId, @PathParam("id") int agreementId) {
        Database db = new Database();
        InternshipAgreement agreement = db.getInternshipAgreement(agreementId);
        if (agreement == null) return Response.status(Response.Status.NOT_FOUND).build();
        if (userId != agreement.getStudent().getId() &&
            userId != agreement.getPartner().getId() &&
            userId != agreement.getCoordinator().getId() &&
            userId != db.getFSD().getId()) //TODO: Administrator?
                return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        InternshipAgreementState state = agreement.getState();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(InternshipAgreementState.class, new InternshipAgreementStateAdapter()).create();
        return Response.ok(gson.toJson(state), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    public Response changeState(@HeaderParam("id") int userId, @PathParam("id") int agreementId, @QueryParam("accept") boolean accept) {
        Database db = new Database();
        InternshipAgreement agreement = db.getInternshipAgreement(agreementId);
        if (agreement == null) return Response.status(Response.Status.NOT_FOUND).build();
        if (userId != agreement.getStudent().getId() &&
            userId != agreement.getPartner().getId() &&
            userId != agreement.getCoordinator().getId() &&
            userId != db.getFSD().getId()) //TODO: Administrator?
                return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        InternshipAgreementState state = agreement.getState();
        InternshipAgreementEvent event = db.getActor(userId).getInternshipAgreementEvent(accept);
        state = StateMachine.makeInternshipAgreementTransition(state, event);
        agreement.setState(state);
        db.update(agreement);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(InternshipAgreementState.class, new InternshipAgreementStateAdapter()).create();
        return Response.ok(gson.toJson(state), MediaType.APPLICATION_JSON).build();
    }
}
