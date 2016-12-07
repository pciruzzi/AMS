package fr.insa.ams.ws;

import com.google.gson.Gson;
import fr.insa.ams.FSD;
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

public class FsdWSTest {

    public FsdWSTest() {
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
    public void shouldCreateFSD() throws URISyntaxException, IOException {
        HttpResponse response = WebUtils.createFSD();
        assertEquals(WebUtils.RESOURCE_CREATED, response.getStatusLine().getStatusCode());
        System.out.println("Createad at: " + response.getLastHeader("Location").getValue());
     }

    @Test
    public void shouldNotBeAbleToCreateTwiceFSD() throws URISyntaxException, IOException {
        HttpResponse response = WebUtils.createFSD();
        assertEquals(WebUtils.RESOURCE_CREATED, response.getStatusLine().getStatusCode());
        response = WebUtils.createFSD();
        assertEquals(WebUtils.CONFLICT, response.getStatusLine().getStatusCode());
    }

    @Test
    public void shouldGetFSD() throws URISyntaxException, IOException {
        HttpResponse response = WebUtils.createFSD();
        assertEquals(WebUtils.RESOURCE_CREATED, response.getStatusLine().getStatusCode());
        HttpClient client = HttpClients.createDefault();
        URI uri = new URIBuilder().setPath(WebUtils.FSD).build();
        HttpGet get = new HttpGet(uri);
        response = client.execute(get);
        assertEquals(WebUtils.SUCCESS, response.getStatusLine().getStatusCode());

        InputStream input = response.getEntity().getContent();
        String json = IOUtils.toString(input, "UTF-8");
        System.out.println("JSON: " + json);
        FSD fsd = new Gson().fromJson(json, FSD.class);
        assertEquals("FSD", fsd.getName());
    }

}