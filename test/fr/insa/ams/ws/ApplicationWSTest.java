package fr.insa.ams.ws;

import fr.insa.ams.Web;

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
import org.junit.Test;
import static org.junit.Assert.*;

public class ApplicationWSTest {

    public ApplicationWSTest() {
    }

    @Before
    public void setUp() throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpPut put = new HttpPut(Web.DATABASE);
        client.execute(put);
    }

    @After
    public void tearDown() throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpDelete delete = new HttpDelete(Web.DATABASE);
        client.execute(delete);
    }

    @Test
    public void shouldGetApplications() throws IOException, URISyntaxException {
        HttpClient client = HttpClients.createDefault();
        URI uri = new URIBuilder().setPath(Web.APPLICATIONS)
                                             .setParameter("id", "1")
                                             .build();
        HttpGet get = new HttpGet(uri);
        get.addHeader("id", "1");
        HttpResponse response = client.execute(get);
//        InputStream input = response.getEntity().getContent();
//        System.out.println(IOUtils.toString(input, "UTF-8"));
        assertEquals(Web.SUCCESS, response.getStatusLine().getStatusCode());
    }

}