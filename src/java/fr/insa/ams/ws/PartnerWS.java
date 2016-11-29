package fr.insa.ams.ws;

import com.google.gson.Gson;
import fr.insa.ams.Actor;
import fr.insa.ams.Database;
import fr.insa.ams.Partner;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("partners")
public class PartnerWS {
    @Context
    private UriInfo context;

    public PartnerWS() {
    }

    @GET
    @Produces("application/json")
    public String getPartner(@QueryParam("id") int id) {
        Database db = new Database();
        Actor partner = db.getActor(id);
        return new Gson().toJson(partner);
    }

    @PUT
    @Consumes("application/json")
    public void addPartner(@QueryParam("name") String name, @QueryParam("address") String address, @QueryParam("telephone") String telephone) {
        Database db = new Database();
        Partner partner = new Partner(name, address, telephone);
        db.add(partner);
    }
}
