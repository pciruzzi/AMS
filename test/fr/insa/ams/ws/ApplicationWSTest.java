package fr.insa.ams.ws;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.insa.ams.WebUtils;

import fr.insa.ams.stateMachine.ApplicationState;
import fr.insa.ams.stateMachine.InternshipAgreementState;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
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
        WebUtils.createStudent("pablo", 5, "IL");
        WebUtils.createPartner("Airbus", "Toulouse", "769379998");
        WebUtils.createClassCoordinator("Pierre", 5, "IL");
        HttpResponse response = WebUtils.createApplication(2, 2, 3, 28);
        assertEquals(WebUtils.RESOURCE_CREATED, response.getStatusLine().getStatusCode());
        System.out.println("Createad at: " + response.getLastHeader("Location").getValue());
    }

    @Test
    public void shouldNotCreateApplicationWhenTheStudentIsNotTheUser() throws URISyntaxException, IOException {
        WebUtils.createStudent("pablo", 5, "IL");
        WebUtils.createPartner("Airbus", "Toulouse", "769379998");
        WebUtils.createClassCoordinator("Pierre", 5, "IR");
        HttpResponse response = WebUtils.createApplication(3, 2, 3, 28);
        assertEquals(WebUtils.UNAUTHORIZED, response.getStatusLine().getStatusCode());
    }

    @Test
    public void shouldGetApplications() throws IOException, URISyntaxException {
        URI uri = new URIBuilder().setPath(WebUtils.APPLICATIONS)
                                             .setParameter("id", "2")
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        get.addHeader("id", "2");
        HttpResponse response = client.execute(get);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());

        InputStream input = response.getEntity().getContent();
        System.out.println("Content: " + IOUtils.toString(input, "UTF-8"));
    }

    @Test
    public void shouldNotBeAbleToGetApplicationsOfOthers() throws URISyntaxException, IOException {
        URI uri = new URIBuilder().setPath(WebUtils.APPLICATIONS)
                                             .setParameter("id", "2")
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        get.addHeader("id", "3");
        HttpResponse response = client.execute(get);
        assertEquals(WebUtils.UNAUTHORIZED, response.getStatusLine().getStatusCode());
    }

    @Test
    public void shouldGetCorrectAplications() throws URISyntaxException, IOException {
        WebUtils.createStudent("pablo", 5, "IL");
        WebUtils.createPartner("Airbus", "Toulouse", "769379998");
        WebUtils.createClassCoordinator("Pierre", 5, "IL");
        WebUtils.createStudent("pepe", 5, "IL");
        WebUtils.createApplication(2, 2, 3, 28);
        WebUtils.createApplication(5, 5, 3, 28);

        URI uri = new URIBuilder().setPath(WebUtils.APPLICATIONS)
                                             .setParameter("id", "2")
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        get.addHeader("id", "2");
        HttpResponse response = client.execute(get);

        InputStream input = response.getEntity().getContent();
        String json = IOUtils.toString(input, "UTF-8");
        JsonElement jelement = new Gson().fromJson(json, JsonElement.class);
        JsonObject jobject = jelement.getAsJsonArray().get(0).getAsJsonObject();
        assertEquals(jobject.get("id").getAsInt(), 1);
        System.out.println("Content of id=2:\n" + json);


        uri = new URIBuilder().setPath(WebUtils.APPLICATIONS)
                                             .setParameter("id", "3")
                                             .build();
        client = HttpClients.createDefault();
        get = new HttpGet(uri);
        get.addHeader("id", "3");
        response = client.execute(get);

        input = response.getEntity().getContent();
        json = IOUtils.toString(input, "UTF-8");
        jelement = new Gson().fromJson(json, JsonElement.class);
        jobject = jelement.getAsJsonArray().get(0).getAsJsonObject();
        assertEquals(jobject.get("id").getAsInt(), 1);
        jobject = jelement.getAsJsonArray().get(1).getAsJsonObject();
        assertEquals(jobject.get("id").getAsInt(), 2);
        System.out.println("Content of id=3:\n" + json);
    }

    @Test
    public void shouldGetApplicationState() throws URISyntaxException, IOException {
        WebUtils.createStudent("pablo", 5, "IL");
        WebUtils.createPartner("Airbus", "Toulouse", "769379998");
        WebUtils.createClassCoordinator("Pierre", 5, "IL");
        WebUtils.createApplication(2, 2, 3, 28);

        URI uri = new URIBuilder().setPath(WebUtils.APPLICATIONS + "/1/state").build();
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        get.addHeader("id", "2");
        HttpResponse response = client.execute(get);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());

        InputStream input = response.getEntity().getContent();
        System.out.println("Content: " + IOUtils.toString(input, "UTF-8"));
    }

    @Test
    public void shouldNotBeAbleToGetApplicationStateWhenNotMineApplication() throws URISyntaxException, IOException {
        WebUtils.createStudent("pablo", 5, "IL");
        WebUtils.createPartner("Airbus", "Toulouse", "769379998");
        WebUtils.createClassCoordinator("Pierre", 5, "IL");
        WebUtils.createFSD();
        WebUtils.createApplication(2, 2, 3, 28);

        URI uri = new URIBuilder().setPath(WebUtils.APPLICATIONS + "/1/state").build();
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        get.addHeader("id", "6");
        HttpResponse response = client.execute(get);
        assertEquals(WebUtils.UNAUTHORIZED, response.getStatusLine().getStatusCode());
    }

    @Test
    public void studentShouldBeAbleToMakeTheApplicationProcess() throws URISyntaxException, IOException {
        WebUtils.createStudent("pablo", 5, "IL");
        WebUtils.createPartner("Airbus", "Toulouse", "769379998");
        WebUtils.createClassCoordinator("Pierre", 5, "IL");
        WebUtils.createFSD();
        WebUtils.createApplication(2, 2, 3, 28);


        URI uri = new URIBuilder().setPath(WebUtils.APPLICATIONS + "/1")
                                             .setParameter("accept", "true")
                                             .build();
        HttpClient client = HttpClients.createDefault();
        HttpPut put = new HttpPut(uri);
        put.addHeader("id", "3");
        HttpResponse response = client.execute(put);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());

        InputStream input = response.getEntity().getContent();
        String json = IOUtils.toString(input, "UTF-8");
        System.out.println("Content: " + json);
        JsonElement jelement = new Gson().fromJson(json, JsonElement.class);
        assertEquals(ApplicationState.WAITING_CC.toString(), jelement.getAsJsonObject().get("state").getAsString());


        put = new HttpPut(uri);
        put.addHeader("id", "4");
        response = client.execute(put);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());

        input = response.getEntity().getContent();
        json = IOUtils.toString(input, "UTF-8");
        System.out.println("Content: " + json);
        jelement = new Gson().fromJson(json, JsonElement.class);
        assertEquals(ApplicationState.WAITING_FSD.toString(), jelement.getAsJsonObject().get("state").getAsString());


        put = new HttpPut(uri);
        put.addHeader("id", "5");
        response = client.execute(put);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());

        input = response.getEntity().getContent();
        json = IOUtils.toString(input, "UTF-8");
        System.out.println("Content: " + json);
        jelement = new Gson().fromJson(json, JsonElement.class);
        assertEquals(ApplicationState.ACCEPTED.toString(), jelement.getAsJsonObject().get("state").getAsString());
    }

    @Test
    public void shouldNotBeAbleToCreateApplicationWhenDoesntExistCoordinatorForStudent() throws URISyntaxException, IOException {
        WebUtils.createStudent("pablo", 5, "IL");
        WebUtils.createPartner("Airbus", "Toulouse", "769379998");
        WebUtils.createClassCoordinator("Pierre", 4, "GM");
        HttpResponse response = WebUtils.createApplication(2, 2, 3, 28);
        assertEquals(WebUtils.CONFLICT, response.getStatusLine().getStatusCode());
    }

    @Test
    public void shouldGetCorrectAplicationsByOffers() throws URISyntaxException, IOException {
        WebUtils.createStudent("pablo", 5, "IL");
        WebUtils.createPartner("Airbus", "Toulouse", "769379998");
        WebUtils.createClassCoordinator("Pierre", 5, "IL");
        WebUtils.createStudent("pepe", 5, "IL");
        WebUtils.createApplication(2, 2, 3, 28);
        WebUtils.createApplication(5, 5, 3, 29);
        WebUtils.createApplication(5, 5, 3, 28);

        URI uri = new URIBuilder().setPath(WebUtils.APPLICATIONS + "/offers/28").build();
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        get.addHeader("id", "2");
        HttpResponse response = client.execute(get);

        InputStream input = response.getEntity().getContent();
        String json = IOUtils.toString(input, "UTF-8");
        System.out.println("Content of offer=28:\n" + json);
        JsonElement jelement = new Gson().fromJson(json, JsonElement.class);
        JsonObject jobject = jelement.getAsJsonArray().get(0).getAsJsonObject();
        assertEquals(jobject.get("id").getAsInt(), 1);
        jobject = jelement.getAsJsonArray().get(1).getAsJsonObject();
        assertEquals(jobject.get("id").getAsInt(), 3);
    }

    @Test
    public void administratorShouldBeAbleToDeleteApplication() throws URISyntaxException, IOException {
        WebUtils.createStudent("pablo", 5, "IL");
        WebUtils.createPartner("Airbus", "Toulouse", "769379998");
        WebUtils.createClassCoordinator("Pierre", 5, "IL");
        WebUtils.createStudent("pepe", 5, "IL");
        WebUtils.createApplication(2, 2, 3, 28);

        URI uri = new URIBuilder().setPath(WebUtils.APPLICATIONS + "/1").build();
        HttpClient client = HttpClients.createDefault();
        HttpDelete delete = new HttpDelete(uri);
        delete.addHeader("id", "1");
        HttpResponse response = client.execute(delete);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());

        uri = new URIBuilder().setPath(WebUtils.APPLICATIONS + "/1/state").build();
        HttpGet get = new HttpGet(uri);
        get.addHeader("id", "1");
        response = client.execute(get);
        assertEquals(WebUtils.NOT_FOUND, response.getStatusLine().getStatusCode());
    }

    @Test
    public void shouldCreateInternshipAgreementWhenFinishingApplicationProcess() throws URISyntaxException, IOException {
        this.studentShouldBeAbleToMakeTheApplicationProcess();
        URI uri = new URIBuilder().setPath(WebUtils.AGREEMENTS + "/1/state").build();
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        get.addHeader("id", "2");
        HttpResponse response = client.execute(get);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());

        InputStream input = response.getEntity().getContent();
        String json = IOUtils.toString(input, "UTF-8");
        System.out.println("Content: " + json);
        JsonElement jelement = new Gson().fromJson(json, JsonElement.class);
        assertEquals(InternshipAgreementState.WAITING_STUDENT.toString(), jelement.getAsJsonObject().get("state").getAsString());
    }
}