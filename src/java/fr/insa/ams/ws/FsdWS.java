package fr.insa.ams.ws;

import fr.insa.ams.*;
import com.google.gson.Gson;
import java.net.URI;
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

@Path("fsd")
public class FsdWS {
    @Context
    private UriInfo context;

    public FsdWS() {
    }

    @GET
    @Produces("application/json")
    // TODO: What if that id is not fsd?
    public Response getFSD(@HeaderParam("id") int userId, @PathParam("id") int id) {
        // TODO: Is everyone able to see the profile, or just himself?
//        if (userId != id) return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        Database db = new Database();
        Actor partner = db.getActor(id);
        return Response.ok(new Gson().toJson(partner), MediaType.APPLICATION_JSON).build();
    }

    @POST
    public Response addFSD(@QueryParam("password") String password, @QueryParam("email") String email) {
        Database db = new Database();
        FSD fsd = new FSD(password, email);
        int fsdId = db.add(fsd);
        return Response.created(URI.create(String.valueOf(fsdId))).build();
    }
}
