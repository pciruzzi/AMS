package fr.insa.ams.ws;

import com.google.gson.Gson;
import fr.insa.ams.Actor;
import fr.insa.ams.ClassCoordinator;
import fr.insa.ams.Database;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("classCoordinators/{id}")
public class ClassCoordinatorWS {
    @Context
    private UriInfo context;

    public ClassCoordinatorWS() {
    }

    @GET
    @Produces("application/json")
    // TODO: What if that id is not a class coordinator?
    public String getCoordinator(@PathParam("id") int id) {
        Database db = new Database();
        Actor coordinator = db.getActor(id);
        return new Gson().toJson(coordinator);
    }

    @PUT
    @Consumes("application/json")
    public void addCoordinator(@QueryParam("name") String name, @QueryParam("year") int year, @QueryParam("pathway") String pathway) {
        Database db = new Database();
        ClassCoordinator coordinator = new ClassCoordinator(name, year, pathway);
        db.add(coordinator);
    }
}
