package fr.insa.ams.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.insa.ams.Group;
import fr.insa.ams.Partner;
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

public class PartnerWSTest {

    public PartnerWSTest() {
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
    public void shouldCreatePartner() throws URISyntaxException, IOException {
        HttpResponse response = WebUtils.createPartner("Airbus", "Toulouse", "769379998");
        assertEquals(WebUtils.RESOURCE_CREATED, response.getStatusLine().getStatusCode());
        System.out.println("Createad at: " + response.getLastHeader("Location").getValue());
     }

    @Test
    public void shouldGetPartner() throws URISyntaxException, IOException {
        WebUtils.createPartner("Airbus", "Toulouse", "769379998");
        HttpClient client = HttpClients.createDefault();
        URI uri = new URIBuilder().setPath(WebUtils.PARTNERS + "/1")
                                          .setParameter("id", "1")
                                          .build();
        HttpGet get = new HttpGet(uri);
        HttpResponse response = client.execute(get);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());

        InputStream input = response.getEntity().getContent();
        String json = IOUtils.toString(input, "UTF-8");
        System.out.println(json);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapter(Group.class, new GroupAdapter()).create();
        Partner partner = gson.fromJson(json, Partner.class);
        assertEquals(1, partner.getId());
        assertEquals("Toulouse", partner.getAddress());
    }

    @Test
     public void shouldNotGetPartnerIfIdIsNotOfIt() throws URISyntaxException, IOException {
        WebUtils.createClassCoordinator("pierre", 5, "IL");
        HttpClient client = HttpClients.createDefault();
        URI uri = new URIBuilder().setPath(WebUtils.PARTNERS + "/1")
                                          .setParameter("id", "1")
                                          .build();
        HttpGet get = new HttpGet(uri);
        HttpResponse response = client.execute(get);
        assertEquals(WebUtils.BAD_REQUEST, response.getStatusLine().getStatusCode());
     }

}