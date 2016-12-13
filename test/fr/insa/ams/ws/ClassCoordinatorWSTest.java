package fr.insa.ams.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.insa.ams.ClassCoordinator;
import fr.insa.ams.Group;
import fr.insa.ams.WebUtils;
import fr.insa.ams.json.GroupAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClassCoordinatorWSTest {

    public ClassCoordinatorWSTest() {
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
    public void shouldCreateCoordinator() throws URISyntaxException, IOException {
        HttpResponse response = WebUtils.createClassCoordinator("Pierre", 4, "IR");
        assertEquals(WebUtils.RESOURCE_CREATED, response.getStatusLine().getStatusCode());
        System.out.println("Createad at: " + response.getLastHeader("Location").getValue());
    }

    @Test
    public void shouldNotBeAbleToCreateCoordinatorForSameYearAndPathway() throws URISyntaxException, IOException {
        HttpResponse response = WebUtils.createClassCoordinator("Pierre", 4, "IR");
        assertEquals(WebUtils.RESOURCE_CREATED, response.getStatusLine().getStatusCode());
        response = WebUtils.createClassCoordinator("Remi", 4, "IR");
        assertEquals(WebUtils.CONFLICT, response.getStatusLine().getStatusCode());
    }

    @Test
    public void shouldGetCoordinator() throws URISyntaxException, IOException {
        WebUtils.createClassCoordinator("Pierre", 5, "GM");
        HttpClient client = HttpClients.createDefault();
        URI uri = new URIBuilder().setPath(WebUtils.COORDINATORS + "/2")
                                          .setParameter("id", "2")
                                          .build();
        HttpGet get = new HttpGet(uri);
        HttpResponse response = client.execute(get);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());

        InputStream input = response.getEntity().getContent();
        String json = IOUtils.toString(input, "UTF-8");
        System.out.println(json);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(Group.class, new GroupAdapter()).create();
        ClassCoordinator coordinator = gson.fromJson(json, ClassCoordinator.class);
        assertEquals(2, coordinator.getId());
        assertEquals("GM", coordinator.getPathway());
    }

    @Test
     public void shouldNotGetCoordinatorIfIdIsNotOfIt() throws URISyntaxException, IOException {
        WebUtils.createStudent("pierre", 5, "IL");
        HttpClient client = HttpClients.createDefault();
        URI uri = new URIBuilder().setPath(WebUtils.COORDINATORS + "/2")
                                          .setParameter("id", "2")
                                          .build();
        HttpGet get = new HttpGet(uri);
        HttpResponse response = client.execute(get);
        assertEquals(WebUtils.BAD_REQUEST, response.getStatusLine().getStatusCode());
     }

}