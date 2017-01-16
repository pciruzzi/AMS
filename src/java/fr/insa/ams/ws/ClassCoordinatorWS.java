package fr.insa.ams.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.insa.ams.Actor;
import fr.insa.ams.ClassCoordinator;
import fr.insa.ams.Database;
import fr.insa.ams.Group;
import fr.insa.ams.Login;
import fr.insa.ams.json.ClassCoordinatorAdapter;
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

@Path("classCoordinators")
public class ClassCoordinatorWS {
    @Context
    private UriInfo context;

    public ClassCoordinatorWS() {
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getCoordinator(@HeaderParam("id") int userId, @PathParam("id") int id) {
        Database db = new Database();
        Actor coordinator = db.getActor(id);
        if (coordinator == null) return Response.status(Response.Status.NOT_FOUND).build();
        if (! (coordinator instanceof ClassCoordinator)) return Response.status(Response.Status.BAD_REQUEST).build();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(ClassCoordinator.class, new ClassCoordinatorAdapter()).create();
        return Response.ok(gson.toJson(coordinator), MediaType.APPLICATION_JSON).build();
    }

    @POST
    public Response addCoordinator(String jsonCoordinator) {
        JsonObject jobject = new Gson().fromJson(jsonCoordinator, JsonElement.class).getAsJsonObject();
        String name = jobject.get("name").getAsString();
        String password = jobject.get("password").getAsString();
        String email = jobject.get("email").getAsString();
        int year = jobject.get("year").getAsInt();
        String pathway = jobject.get("pathway").getAsString();

        Database db = new Database();
        if (db.existsCoordinator(year, pathway)) return Response.status(Response.Status.CONFLICT).build();
        Group group = new Group("CLASS_COORDINATOR");
        db.addGroup(group);
        ClassCoordinator coordinator = new ClassCoordinator(name, email, year, pathway, group);
        int coordinatorId = db.add(coordinator);
        Login login = new Login(coordinatorId, password, group);
        db.add(login);
        return Response.created(URI.create(String.valueOf(coordinatorId))).build();
    }
}
