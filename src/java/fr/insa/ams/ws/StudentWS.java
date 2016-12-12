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
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
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
        Database db = new Database();
        Actor student = db.getActor(id);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(Student.class, new StudentAdapter()).registerTypeAdapter(CV.class, new CVAdapter()).create();
        return Response.ok(gson.toJson(student), MediaType.APPLICATION_JSON).build();
    }

    @POST
    public Response addStudent(@QueryParam("name") String name, @QueryParam("password") String password,
                                               @QueryParam("email") String email, @QueryParam("year") int year,
                                               @QueryParam("pathway") String pathway, @QueryParam("address") String address,
                                               @QueryParam("telephone") String telephone, @QueryParam("group") String groupName) {
        Database db = new Database();
        Group group = new Group(groupName);
        db.addGroup(group);
        Student student = new Student(name, password, email, year, pathway, address, telephone, group);
        int studentId = db.add(student);
        return Response.created(URI.create(String.valueOf(studentId))).build();
    }

    @GET
    @Path("/cvs/{cvId}")
    @Produces("application/pdf")
    public Response getCV(@HeaderParam("id") int userId, @PathParam("cvId") int cvId) {
        Database db = new Database();
        CV cv = db.getCV(cvId);
        File file = new File(cv.getId() + ".pdf"); //TODO: Attention
        return Response.ok(file).header("Content-Disposition", "attachment; filename=\"" + cv.getName() + ".pdf\"").build();
    }

    @DELETE
    @Path("/cvs/{cvId}")
    public Response deleteCV(@HeaderParam("id") int userId, @PathParam("cvId") int cvId) {
        Database db = new Database();
        CV cv = db.getCV(cvId);
        if (cv != null) {
            File file = new File(cv.getId() + ".pdf"); //TODO: Attention
            if (file.delete()) {
                db.delete(cv);
                return Response.ok().build();
            }
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/cvs/{cvId}")
    public Response renameCV(@HeaderParam("id") int userId, @PathParam("cvId") int cvId,
                                              @QueryParam("name") String newName) {
        Database db = new Database();
        CV cv = db.getCV(cvId);
        cv.setName(newName);
        db.update(cv);
        return Response.ok().build();
    }

    @POST
    @Path("/{id}/cvs")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadCV(@HeaderParam("id") int userId, @PathParam("id") int id,
                                            @FormDataParam("file") InputStream uploadedInputStream, @QueryParam("name") String name) {
        // TODO: Name validations? Create userId folder?
        if (userId != id) return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build();
        Database db = new Database();
        CV cv = new CV(name);
        int cvId = db.addCV(cv, userId);
        String filename = cvId + ".pdf";
        System.out.println("Uploading file " + filename);
//        System.out.println(getRelativePath());
        writeToFile(uploadedInputStream, filename);
        System.out.println("File written...");
        return Response.created(URI.create(String.valueOf(cvId))).build();
    }

    private String getRelativePath() {
        Class cls = this.getClass();
        ProtectionDomain pDomain = cls.getProtectionDomain();
        CodeSource cSource = pDomain.getCodeSource();
        URL loc = cSource.getLocation();
        return loc.toString();
    }

    private void writeToFile(InputStream uploadedInputStream, String fileLocation) {
        try {
            File file = new File(fileLocation);
//            System.out.println(file.getAbsolutePath());
//            System.out.println(System.getProperty("user.dir"));
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
