package fr.insa.ams.ws;

import fr.insa.ams.WebUtils;
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

public class ActorsWSTest {

    public ActorsWSTest() {
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
    public void shouldGetActor() throws URISyntaxException, IOException {
        WebUtils.createClassCoordinator("Pierre", 5, "GM");
        WebUtils.createFSD();
        WebUtils.createPartner("Airbus", "Toulouse", "+3354454515");
        WebUtils.createStudent("pablo", 5, "IL");

        HttpClient client = HttpClients.createDefault();
        URI uri = new URIBuilder().setPath(WebUtils.ACTORS + "/1")
                                          .setParameter("id", "1")
                                          .build();
        HttpGet get = new HttpGet(uri);
        HttpResponse response = client.execute(get);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());
        System.out.println(IOUtils.toString(response.getEntity().getContent(), "UTF-8"));

        client = HttpClients.createDefault();
        uri = new URIBuilder().setPath(WebUtils.ACTORS + "/2")
                                        .setParameter("id", "2")
                                        .build();
        get = new HttpGet(uri);
        response = client.execute(get);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());
        System.out.println(IOUtils.toString(response.getEntity().getContent(), "UTF-8"));

        client = HttpClients.createDefault();
        uri = new URIBuilder().setPath(WebUtils.ACTORS + "/3")
                                        .setParameter("id", "3")
                                        .build();
        get = new HttpGet(uri);
        response = client.execute(get);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());
        System.out.println(IOUtils.toString(response.getEntity().getContent(), "UTF-8"));

        client = HttpClients.createDefault();
        uri = new URIBuilder().setPath(WebUtils.ACTORS + "/4")
                                        .setParameter("id", "4")
                                        .build();
        get = new HttpGet(uri);
        response = client.execute(get);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());
        System.out.println(IOUtils.toString(response.getEntity().getContent(), "UTF-8"));
    }
}