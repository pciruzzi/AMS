package fr.insa.ams;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("student")
public class StudentWS {
    @Context
    private UriInfo context;

    public StudentWS() {
    }

    @GET
    @Produces("application/json")
    public String getStudent(@QueryParam("id") int id) {
        Database db = new Database();
        Actor actor = db.getActor(id);
        return new Gson().toJson(actor);
    }

    @PUT
    @Consumes("application/json")
    public void addStudent(@QueryParam("name") String name, @QueryParam("year") int year) {
        Database db = new Database();
        Student student = new Student(name, year);
        db.add(student);
    }
}
