package fr.insa.ams.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.insa.ams.Actor;
import fr.insa.ams.Database;
import fr.insa.ams.Group;
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
    @Produces("application/json")
    public Response getPartners() {
        // TODO: Retrieve all partners?
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    // TODO: What if that id is not a partner?
    public Response getPartner(@HeaderParam("id") int userId, @PathParam("id") int id) {
        Database db = new Database();
        Actor partner = db.getActor(id);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(Partner.class, new PartnerAdapter()).create();
        return Response.ok(gson.toJson(partner), MediaType.APPLICATION_JSON).build();
    }

    @POST
    public Response addPartner(@QueryParam("name") String name, @QueryParam("password") String password,
                                               @QueryParam("email") String email, @QueryParam("address") String address,
                                               @QueryParam("telephone") String telephone, @QueryParam("location") String location,
                                               @QueryParam("group") String groupName) {
        Database db = new Database();
        Group group = new Group(groupName);
        db.addGroup(group);
        Partner partner = new Partner(name, password, email, address, telephone, location, group);
        int partnerId = db.add(partner);
        return Response.created(URI.create(String.valueOf(partnerId))).build();
    }
}
