package fr.insa.ams.ws;

import com.google.gson.Gson;
import fr.insa.ams.Actor;
import fr.insa.ams.Database;
import fr.insa.ams.Partner;
import java.net.URI;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
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
    public Response getPartner(@PathParam("id") int id) {
        Database db = new Database();
        Actor partner = db.getActor(id);
        return Response.ok(new Gson().toJson(partner), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Consumes("application/json")
    public Response addPartner(@QueryParam("name") String name, @QueryParam("address") String address, @QueryParam("telephone") String telephone) {
        Database db = new Database();
        Partner partner = new Partner(name, address, telephone);
        int partnerId = db.add(partner);
        return Response.created(URI.create("partners/" + partnerId)).build();
    }
}
