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
    public Response getFSD(@HeaderParam("id") int userId) {
        // TODO: Is everyone able to see the profile, or just himself?
//        if (userId != db.getFSD().getId()) return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        Database db = new Database();
        if (! db.existsFSD()) return Response.status(Response.Status.NOT_FOUND).build();
        Actor fsd = db.getActor(db.getFSD().getId());
        return Response.ok(new Gson().toJson(fsd), MediaType.APPLICATION_JSON).build();
    }

    @POST
    public Response addFSD(@QueryParam("password") String password, @QueryParam("email") String email) {
        Database db = new Database();
        FSD fsd = new FSD(password, email);
        int fsdId = db.add(fsd);
        return (fsdId != -1) ? Response.created(URI.create(String.valueOf(fsdId))).build() :
                                       Response.status(Response.Status.CONFLICT).build();
    }
}
