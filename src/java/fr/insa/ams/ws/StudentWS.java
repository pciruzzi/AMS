package fr.insa.ams.ws;

import fr.insa.ams.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.insa.ams.json.StudentAdapter;
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

@Path("students")
public class StudentWS {
    @Context
    private UriInfo context;

    public StudentWS() {
    }

    @GET
    @Produces("application/json")
    public Response getStudents() {
        // TODO: Retrieve all students?
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    // TODO: What if that id is not a student?
    public Response getStudent(@HeaderParam("id") int userId, @PathParam("id") int id) {
        // TODO: Is everyone able to see the profile, or just himself?
//        if (userId != id) return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        Database db = new Database();
        Actor student = db.getActor(id);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(Student.class, new StudentAdapter()).create();
        return Response.ok(gson.toJson(student), MediaType.APPLICATION_JSON).build();
    }

    @POST
    public Response addStudent(@QueryParam("name") String name, @QueryParam("password") String password,
                                               @QueryParam("email") String email, @QueryParam("year") int year,
                                               @QueryParam("pathway") String pathway, @QueryParam("address") String address,
                                               @QueryParam("telephone") String telephone) {
        Database db = new Database();
        Group group = new Group("students");
        db.addGroup(group);
        Student student = new Student(name, password, email, year, pathway, address, telephone, group);
        int studentId = db.add(student);
        return Response.created(URI.create(String.valueOf(studentId))).build();
    }
}
