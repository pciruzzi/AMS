package fr.insa.ams.ws;

import com.google.gson.Gson;
import fr.insa.ams.Actor;
import fr.insa.ams.ClassCoordinator;
import fr.insa.ams.Database;
import java.net.URI;
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

@Path("classCoordinators")
public class ClassCoordinatorWS {
    @Context
    private UriInfo context;

    public ClassCoordinatorWS() {
    }

    @GET
    @Produces("application/json")
    public Response getClassCoordinators() {
        // TODO: Retrieve all coordinators?
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    // TODO: What if that id is not a class coordinator?
    public Response getCoordinator(@HeaderParam("id") int userId, @PathParam("id") int id) {
        Database db = new Database();
        Actor coordinator = db.getActor(id);
        return Response.ok(new Gson().toJson(coordinator), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Consumes("application/json")
    public Response addCoordinator(@QueryParam("name") String name, @QueryParam("year") int year, @QueryParam("pathway") String pathway) {
        Database db = new Database();
        ClassCoordinator coordinator = new ClassCoordinator(name, year, pathway);
        int coordinatorId = db.add(coordinator);
        return Response.created(URI.create("classCoordinators/" + coordinatorId)).build();
    }
}
