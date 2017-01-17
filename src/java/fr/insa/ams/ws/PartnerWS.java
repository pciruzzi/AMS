package fr.insa.ams.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.insa.ams.Actor;
import fr.insa.ams.Database;
import fr.insa.ams.Group;
import fr.insa.ams.Login;
import fr.insa.ams.Partner;
import fr.insa.ams.json.PartnerAdapter;
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

@Path("partners")
public class PartnerWS {
    @Context
    private UriInfo context;

    public PartnerWS() {
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getPartner(@HeaderParam("id") int userId, @PathParam("id") int id) {
        Database db = new Database();
        Actor partner = db.getActor(id);
        if (partner == null) return Response.status(Response.Status.NOT_FOUND).build();
        if (! (partner instanceof Partner)) return Response.status(Response.Status.BAD_REQUEST).build();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(Partner.class, new PartnerAdapter()).create();
        return Response.ok(gson.toJson(partner), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/names")
    @Produces("application/json")
    public Response getPartnerByName(@HeaderParam("id") int userId, String name) {
        Database db = new Database();
        Actor partner = db.getActorByName(name);
        if (partner == null) return Response.status(Response.Status.NOT_FOUND).build();
        if (! (partner instanceof Partner)) return Response.status(Response.Status.BAD_REQUEST).build();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(Partner.class, new PartnerAdapter()).create();
        return Response.ok(gson.toJson(partner), MediaType.APPLICATION_JSON).build();
    }

    @POST
    public Response addPartner(String jsonPartner) {
        JsonObject jobject = new Gson().fromJson(jsonPartner, JsonElement.class).getAsJsonObject();
        String name = jobject.get("name").getAsString();
        String password = jobject.get("password").getAsString();
        String email = jobject.get("email").getAsString();
        String address = jobject.get("address").getAsString();
        String telephone = jobject.get("telephone").getAsString();
        String location = jobject.get("location").getAsString();

        Database db = new Database();
        if (db.getActorByName(name) != null) return Response.status(Response.Status.CONFLICT).build();
        Group group = new Group("PARTNER");
        db.addGroup(group);
        Partner partner = new Partner(name, email, address, telephone, location, group);
        int partnerId = db.add(partner);
        Login login = new Login(partnerId, password, group);
        db.add(login);
        return Response.created(URI.create(String.valueOf(partnerId))).build();
    }
}
