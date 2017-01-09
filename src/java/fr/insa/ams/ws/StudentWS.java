package fr.insa.ams.ws;

import fr.insa.ams.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.multipart.FormDataParam;
import fr.insa.ams.json.CVAdapter;
import fr.insa.ams.json.StudentAdapter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("students")
public class StudentWS {
    @Context
    private UriInfo context;

    private final String CVS_FOLDER = "cvs";

    public StudentWS() {
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getStudent(@HeaderParam("id") int userId, @PathParam("id") int id) {
        Database db = new Database();
        Actor student = db.getActor(id);
        if (student == null) return Response.status(Response.Status.NOT_FOUND).build();
        if (! (student instanceof Student)) return Response.status(Response.Status.BAD_REQUEST).build();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(Student.class, new StudentAdapter()).registerTypeAdapter(CV.class, new CVAdapter()).create();
        return Response.ok(gson.toJson(student), MediaType.APPLICATION_JSON).build();
    }

    @POST
    public Response addStudent(@QueryParam("name") String name, @QueryParam("password") String password,
                                               @QueryParam("email") String email, @QueryParam("year") int year,
                                               @QueryParam("pathway") String pathway, @QueryParam("address") String address,
                                               @QueryParam("telephone") String telephone) {
        Database db = new Database();
        Group group = new Group("STUDENT");
        db.addGroup(group);
        Student student = new Student(name, email, year, pathway, address, telephone, group);
        int studentId = db.add(student);
        Login login = new Login(studentId, password, group);
        db.add(login);
        return Response.created(URI.create(String.valueOf(studentId))).build();
    }


    @GET
    @Path("/cvs/{cvId}")
    @Produces("application/pdf")
    public Response getCV(@HeaderParam("id") int userId, @PathParam("cvId") int cvId) {
        Database db = new Database();
        CV cv = db.getCV(cvId);
        // I don't put the || ! cv.getIsAvailable() here because I have to leave it in case someone want to see the CV of an application
        if (cv == null) return Response.status(Response.Status.NOT_FOUND).build();
        File file = new File(CVS_FOLDER + "/" + cv.getId() + ".pdf");
        return Response.ok(file).header("Content-Disposition", "attachment; filename=\"" + cv.getName() + ".pdf\"").build();
    }

    @DELETE
    @Path("/cvs/{cvId}")
    public Response deleteCV(@HeaderParam("id") int userId, @PathParam("cvId") int cvId) {
        Database db = new Database();
        CV cv = db.getCV(cvId);
        if (cv == null || ! cv.getIsAvailable()) return Response.status(Response.Status.NOT_FOUND).build();
        cv.setIsAvailable(false);
        db.update(cv);
        return Response.ok().build();
    }

    @PUT
    @Path("/cvs/{cvId}")
    public Response renameCV(@HeaderParam("id") int userId, @PathParam("cvId") int cvId,
                                              @QueryParam("name") String newName) {
        Database db = new Database();
        CV cv = db.getCV(cvId);
        if (cv == null || ! cv.getIsAvailable()) return Response.status(Response.Status.NOT_FOUND).build();
        cv.setName(newName);
        db.update(cv);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/cvs")
    @Produces("application/json")
    public Response getCVs(@HeaderParam("id") int userId, @PathParam("id") int id) {
        // Only the connected user is able to get his CVs
        if (userId != id) return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        Database db = new Database();
        Actor student = db.getActor(id);
        // Only a student is able to get CVs
        if (student == null) return Response.status(Response.Status.NOT_FOUND).build();
        if (! (student instanceof Student)) return Response.status(Response.Status.BAD_REQUEST).build();

        Set<CV> cvs = ((Student) student).getCvs();
        Set<CV> cvsAvailables = new HashSet<CV>();
        for (CV cv : cvs) {
            if (cv.getIsAvailable()) cvsAvailables.add(cv);
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(CV.class, new CVAdapter()).create();
        return Response.ok(gson.toJson(cvsAvailables), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/{id}/cvs")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadCV(@HeaderParam("id") int userId, @PathParam("id") int id,
                                            @FormDataParam("file") InputStream uploadedInputStream, @QueryParam("name") String name) {
        File file = new File(CVS_FOLDER);
        if (! file.exists() && ! file.mkdir()) return Response.status(Response.Status.NOT_MODIFIED).build();
        // Only the connected user is able to upload a CV
        if (userId != id) return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        Database db = new Database();
        Actor student = db.getActor(id);
        // Only a student is able to upload CVs
        if (student == null) return Response.status(Response.Status.NOT_FOUND).build();
        if (! (student instanceof Student)) return Response.status(Response.Status.BAD_REQUEST).build();

        CV cv = new CV(name);
        int cvId = db.addCV(cv, userId);
        String filename = CVS_FOLDER + "/" + cvId + ".pdf";
        writeToFile(uploadedInputStream, filename);
        return Response.created(URI.create(String.valueOf(cvId))).build();
    }

    private void writeToFile(InputStream uploadedInputStream, String fileLocation) {
        try {
            File file = new File(fileLocation);
            OutputStream out = new FileOutputStream(file);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
