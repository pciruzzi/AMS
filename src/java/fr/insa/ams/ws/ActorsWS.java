package fr.insa.ams.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.insa.ams.Actor;
import fr.insa.ams.CV;
import fr.insa.ams.ClassCoordinator;
import fr.insa.ams.Database;
import fr.insa.ams.FSD;
import fr.insa.ams.Group;
import fr.insa.ams.Partner;
import fr.insa.ams.Student;
import fr.insa.ams.json.CVAdapter;
import fr.insa.ams.json.ClassCoordinatorAdapter;
import fr.insa.ams.json.FSDAdapter;
import fr.insa.ams.json.GroupAdapter;
import fr.insa.ams.json.PartnerAdapter;
import fr.insa.ams.json.StudentAdapter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("actors")
public class ActorsWS {
    @Context
    private UriInfo context;

    public ActorsWS() {
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getActor(@HeaderParam("id") int userId, @PathParam("id") int id) {
        Database db = new Database();
        Actor actor = db.getActor(id);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(ClassCoordinator.class, new ClassCoordinatorAdapter())
                                            .registerTypeAdapter(FSD.class, new FSDAdapter())
                                            .registerTypeAdapter(Partner.class, new PartnerAdapter())
                                            .registerTypeAdapter(Student.class, new StudentAdapter())
                                            .registerTypeAdapter(CV.class, new CVAdapter())
                                            .create();
        return Response.ok(gson.toJson(actor), MediaType.APPLICATION_JSON).build();
    }

}
