package fr.insa.ams.ws;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.insa.ams.WebUtils;

import fr.insa.ams.stateMachine.ApplicationState;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ApplicationWSTest {

    public ApplicationWSTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("##################################################");
    }

    @Before
    public void setUp() throws IOException {
        System.out.println("------------------------------------------");
        WebUtils.createDBSession();
    }

    @After
    public void tearDown() throws IOException {
        WebUtils.closeDBSession();
    }

    @Test
    public void shouldCreateApplication() throws URISyntaxException, IOException {
        WebUtils.createStudent("pablo", "5");
        WebUtils.createPartner("Airbus", "Toulouse", "769379998");
        WebUtils.createClassCoordinator("Pierre", "5", "IR");
        HttpResponse response = WebUtils.createApplication(1, 1, 2, 3, 28);
        assertEquals(WebUtils.RESOURCE_CREATED, response.getStatusLine().getStatusCode());
        System.out.println("Createad at: " + response.getLastHeader("Location").getValue());
    }

    @Test
    public void shouldNotCreateApplicationWhenTheStudentIsNotTheUser() throws URISyntaxException, IOException {
        WebUtils.createStudent("pablo", "5");
        WebUtils.createPartner("Airbus", "Toulouse", "769379998");
        WebUtils.createClassCoordinator("Pierre", "5", "IR");
        HttpResponse response = WebUtils.createApplication(2, 1, 2, 3, 28);
        assertEquals(WebUtils.UNAUTHORIZED, response.getStatusLine().getStatusCode());
    }

    @Test
    public void shouldGetApplications() throws IOException, URISyntaxException {
        URI uri = new URIBuilder().setPath(WebUtils.APPLICATIONS)
                                             .setParameter("id", "1")
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        get.addHeader("id", "1");
        HttpResponse response = client.execute(get);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());

        InputStream input = response.getEntity().getContent();
        System.out.println("Content: " + IOUtils.toString(input, "UTF-8"));
    }

    @Test
    public void shouldNotBeAbleToGetApplicationsOfOthers() throws URISyntaxException, IOException {
        URI uri = new URIBuilder().setPath(WebUtils.APPLICATIONS)
                                             .setParameter("id", "1")
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        get.addHeader("id", "2");
        HttpResponse response = client.execute(get);
        assertEquals(WebUtils.UNAUTHORIZED, response.getStatusLine().getStatusCode());
    }

    @Test
    public void shouldGetCorrectAplications() throws URISyntaxException, IOException {
        WebUtils.createStudent("pablo", "5");
        WebUtils.createPartner("Airbus", "Toulouse", "769379998");
        WebUtils.createClassCoordinator("Pierre", "5", "IR");
        WebUtils.createStudent("pepe", "4");
        WebUtils.createApplication(1, 1, 2, 3, 28);
        WebUtils.createApplication(4, 4, 2, 3, 28);

        URI uri = new URIBuilder().setPath(WebUtils.APPLICATIONS)
                                             .setParameter("id", "1")
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        get.addHeader("id", "1");
        HttpResponse response = client.execute(get);

        InputStream input = response.getEntity().getContent();
        String json = IOUtils.toString(input, "UTF-8");
        JsonElement jelement = new Gson().fromJson(json, JsonElement.class);
        JsonObject jobject = jelement.getAsJsonArray().get(0).getAsJsonObject();
        assertEquals(jobject.get("id").getAsInt(), 1);
        System.out.println("Content of id=1:\n" + json);


        uri = new URIBuilder().setPath(WebUtils.APPLICATIONS)
                                             .setParameter("id", "2")
                                             .build();
        client = HttpClients.createDefault();
        get = new HttpGet(uri);
        get.addHeader("id", "2");
        response = client.execute(get);

        input = response.getEntity().getContent();
        json = IOUtils.toString(input, "UTF-8");
        jelement = new Gson().fromJson(json, JsonElement.class);
        jobject = jelement.getAsJsonArray().get(0).getAsJsonObject();
        assertEquals(jobject.get("id").getAsInt(), 1);
        jobject = jelement.getAsJsonArray().get(1).getAsJsonObject();
        assertEquals(jobject.get("id").getAsInt(), 2);
        System.out.println("Content of id=2:\n" + json);
    }

    @Test
    public void shouldGetApplicationState() throws URISyntaxException, IOException {
        WebUtils.createStudent("pablo", "5");
        WebUtils.createPartner("Airbus", "Toulouse", "769379998");
        WebUtils.createClassCoordinator("Pierre", "5", "IR");
        WebUtils.createApplication(1, 1, 2, 3, 28);

        URI uri = new URIBuilder().setPath(WebUtils.APPLICATIONS + "/1/state").build();
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        get.addHeader("id", "1");
        HttpResponse response = client.execute(get);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());

        InputStream input = response.getEntity().getContent();
        System.out.println("Content: " + IOUtils.toString(input, "UTF-8"));
    }

    @Test
    public void shouldNotBeAbleToGetApplicationStateWhenNotMineApplication() throws URISyntaxException, IOException {
        WebUtils.createStudent("pablo", "5");
        WebUtils.createPartner("Airbus", "Toulouse", "769379998");
        WebUtils.createClassCoordinator("Pierre", "5", "IR");
        WebUtils.createApplication(1, 1, 2, 3, 28);

        URI uri = new URIBuilder().setPath(WebUtils.APPLICATIONS + "/1/state").build();
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        get.addHeader("id", "4");
        HttpResponse response = client.execute(get);
        assertEquals(WebUtils.UNAUTHORIZED, response.getStatusLine().getStatusCode());
    }

    @Test
    public void studentShouldBeAbleToMakeTheApplicationProcess() throws URISyntaxException, IOException {
        WebUtils.createStudent("pablo", "5");
        WebUtils.createPartner("Airbus", "Toulouse", "769379998");
        WebUtils.createClassCoordinator("Pierre", "5", "IR");
        WebUtils.createApplication(1, 1, 2, 3, 28);

        URI uri = new URIBuilder().setPath(WebUtils.APPLICATIONS + "/1/state")
                                             .setParameter("accept", "true")
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpPut put = new HttpPut(uri);
        put.addHeader("id", "2");
        HttpResponse response = client.execute(put);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());

        InputStream input = response.getEntity().getContent();
        String json = IOUtils.toString(input, "UTF-8");
        System.out.println("Content: " + json);
        JsonElement jelement = new Gson().fromJson(json, JsonElement.class);
        assertEquals(ApplicationState.WAITING_CC.toString(), jelement.getAsJsonObject().get("state").getAsString());

        put = new HttpPut(uri);
        put.addHeader("id", "3");
        response = client.execute(put);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());

        input = response.getEntity().getContent();
        json = IOUtils.toString(input, "UTF-8");
        System.out.println("Content: " + json);
        jelement = new Gson().fromJson(json, JsonElement.class);
        assertEquals(ApplicationState.ACCEPTED.toString(), jelement.getAsJsonObject().get("state").getAsString());
    }

}