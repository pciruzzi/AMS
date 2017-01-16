package fr.insa.ams.ws;

import fr.insa.ams.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.insa.ams.json.FSDAdapter;
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
    public Response getFSD(@HeaderParam("id") int userId) {
        Database db = new Database();
        if (! db.existsFSD()) return Response.status(Response.Status.NOT_FOUND).build();
        Actor fsd = db.getActor(db.getFSD().getId());
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(FSD.class, new FSDAdapter()).create();
        return Response.ok(gson.toJson(fsd), MediaType.APPLICATION_JSON).build();
    }

    @POST
    public Response addFSD(String jsonFsd) {
        JsonObject jobject = new Gson().fromJson(jsonFsd, JsonElement.class).getAsJsonObject();
        String password = jobject.get("password").getAsString();
        String email = jobject.get("email").getAsString();

        Database db = new Database();
        if (db.existsFSD()) return Response.status(Response.Status.CONFLICT).build();
        Group group = new Group("FSD");
        db.addGroup(group);
        FSD fsd = new FSD(email, group);
        int fsdId = db.add(fsd);
        Login login = new Login(fsdId, password, group);
        db.add(login);
        return Response.created(URI.create(String.valueOf(fsdId))).build();
    }
}
