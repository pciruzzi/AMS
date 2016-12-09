package fr.insa.ams;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;

public class WebUtils {

    public static final String BASE = "http://localhost:8080/AMS/resources";
    public static final String DATABASE = BASE + "/database";
    public static final String APPLICATIONS = BASE + "/applications";
    public static final String COORDINATORS = BASE + "/classCoordinators";
    public static final String PARTNERS = BASE + "/partners";
    public static final String STUDENTS = BASE + "/students";
    public static final String FSD = BASE + "/fsd";

    public static final int SUCCESS = 200;
    public static final int RESOURCE_CREATED = 201;
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
                                             .setParameter("name", name)
                                             .setParameter("password", "password")
                                             .setParameter("email", "a@a.com")
                                             .setParameter("year", String.valueOf(year))
                                             .setParameter("pathway", pathway)
                                             .setParameter("group", "coordinators")
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(uri);
        return client.execute(post);
    }

    public static HttpResponse createPartner(String name, String address, String telephone) throws URISyntaxException, IOException {
        URI uri = new URIBuilder().setPath(PARTNERS)
                                             .setParameter("name", name)
                                             .setParameter("password", "password")
                                             .setParameter("email", "a@a.com")
                                             .setParameter("address", address)
                                             .setParameter("telephone", telephone)
                                             .setParameter("location", "Toulouse")
                                             .setParameter("group", "partners")
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(uri);
        return client.execute(post);
    }

    public static HttpResponse createStudent(String name, int year, String pathway) throws URISyntaxException, IOException {
        URI uri = new URIBuilder().setPath(STUDENTS)
                                             .setParameter("name", name)
                                             .setParameter("password", "password")
                                             .setParameter("email", "a@a.com")
                                             .setParameter("year", String.valueOf(year))
                                             .setParameter("pathway", pathway)
                                             .setParameter("address", "INSA Toulouse")
                                             .setParameter("telephone", "33769379998")
                                             .setParameter("group", "students")
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(uri);
        return client.execute(post);
    }

    public static HttpResponse createFSD() throws URISyntaxException, IOException {
        URI uri = new URIBuilder().setPath(FSD)
                                             .setParameter("password", "password")
                                             .setParameter("email", "a@a.com")
                                             .setParameter("group", "fsd")
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(uri);
        return client.execute(post);
    }

    public static HttpResponse createApplication(int userID, int studentID, int partnerID, int offerID) throws URISyntaxException, IOException {
        URI uri = new URIBuilder().setPath(APPLICATIONS)
                                             .setParameter("studentID", String.valueOf(studentID))
                                             .setParameter("partnerID", String.valueOf(partnerID))
                                             .setParameter("offerID", String.valueOf(offerID))
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(uri);
        post.addHeader("id", String.valueOf(userID));
        return client.execute(post);
    }

}
