package fr.insa.ams;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;

public class WebUtils {

    public static final String BASE = "http://localhost:8080/AMS/resources";
    public static final String DATABASE = BASE + "/database";
    public static final String APPLICATIONS = BASE + "/applications";
    public static final String AGREEMENTS = BASE + "/agreements";
    public static final String ACTORS = BASE + "/actors";
    public static final String COORDINATORS = BASE + "/classCoordinators";
    public static final String PARTNERS = BASE + "/partners";
    public static final String STUDENTS = BASE + "/students";
    public static final String FSD = BASE + "/fsd";

    public static final int SUCCESS = 200;
    public static final int RESOURCE_CREATED = 201;
    public static final int NOT_MODIFIED = 304;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int NOT_FOUND = 404;
    public static final int CONFLICT = 409;

    public static void createDBSession() throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpPut put = new HttpPut(WebUtils.DATABASE);
        client.execute(put);
    }

    public static void closeDBSession() throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpDelete delete = new HttpDelete(WebUtils.DATABASE);
        client.execute(delete);
    }

    public static HttpResponse createClassCoordinator(String name, int year, String pathway) throws URISyntaxException, IOException {
        URI uri = new URIBuilder().setPath(COORDINATORS)
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(uri);
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("password", "password");
        json.addProperty("email", "a@a.com");
        json.addProperty("year", year);
        json.addProperty("pathway", pathway);
        HttpEntity entity = new ByteArrayEntity(json.toString().getBytes("UTF-8"));
        post.setEntity(entity);
        return client.execute(post);
    }

    public static HttpResponse createPartner(String name, String address, String telephone) throws URISyntaxException, IOException {
        URI uri = new URIBuilder().setPath(PARTNERS)
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(uri);
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("password", "password");
        json.addProperty("email", "a@a.com");
        json.addProperty("address", address);
        json.addProperty("telephone", telephone);
        json.addProperty("location", "Toulouse");
        HttpEntity entity = new ByteArrayEntity(json.toString().getBytes("UTF-8"));
        post.setEntity(entity);
        return client.execute(post);
    }

    public static HttpResponse createStudent(String name, int year, String pathway) throws URISyntaxException, IOException {
        URI uri = new URIBuilder().setPath(STUDENTS)
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(uri);
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("password", "password");
        json.addProperty("email", "a@a.com");
        json.addProperty("year", year);
        json.addProperty("pathway", pathway);
        json.addProperty("address", "INSA Toulouse");
        json.addProperty("telephone", "33769379998");
        HttpEntity entity = new ByteArrayEntity(json.toString().getBytes("UTF-8"));
        post.setEntity(entity);
        return client.execute(post);
    }

    public static HttpResponse createFSD() throws URISyntaxException, IOException {
        URI uri = new URIBuilder().setPath(FSD)
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(uri);
        JsonObject json = new JsonObject();
        json.addProperty("password", "password");
        json.addProperty("email", "a@a.com");
        HttpEntity entity = new ByteArrayEntity(json.toString().getBytes("UTF-8"));
        post.setEntity(entity);
        return client.execute(post);
    }

    public static HttpResponse createApplication(int userID, int studentID, int partnerID, int offerID) throws URISyntaxException, IOException {
        URI uri = new URIBuilder().setPath(APPLICATIONS)
                                             .setParameter("studentID", String.valueOf(studentID))
                                             .setParameter("partnerID", String.valueOf(partnerID))
                                             .setParameter("offerID", String.valueOf(offerID))
                                             .setParameter("cvID", "-1")
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(uri);
        post.addHeader("id", String.valueOf(userID));
        String coverLetter = "a cover letter";
        HttpEntity entity = new ByteArrayEntity(coverLetter.getBytes("UTF-8"));
        post.setEntity(entity);
        return client.execute(post);
    }

}
