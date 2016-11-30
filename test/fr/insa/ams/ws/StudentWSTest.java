package fr.insa.ams.ws;

import com.google.gson.Gson;
import fr.insa.ams.Student;
import fr.insa.ams.Web;
import fr.insa.ams.hibernate.HibernateUtil;
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
import org.junit.Test;
import static org.junit.Assert.*;

public class StudentWSTest {

    public StudentWSTest() {
    }

    @Before
    public void setUp() {
        HibernateUtil.createSessionFactory();
    }

    @After
    public void tearDown() {
        HibernateUtil.closeSessionFactory();
    }

    private HttpResponse createStudent(String name, String year) throws URISyntaxException, IOException {
        HttpClient client = HttpClients.createDefault();
        URI uri = new URIBuilder().setPath(Web.STUDENTS)
                                             .setParameter("name", name)
                                             .setParameter("year", year)
                                             .build();
        HttpPut put = new HttpPut(uri);
        return client.execute(put);
    }

     @Test
     public void shouldCreateStudent() throws URISyntaxException, IOException {
        HttpResponse response = createStudent("pepe", "4");
//        InputStream input = response.getEntity().getContent();
//        System.out.println(IOUtils.toString(input, "UTF-8"));
        assertEquals(Web.RESOURCE_CREATED, response.getStatusLine().getStatusCode());
     }

     @Test
     public void shouldGetStudent() throws URISyntaxException, IOException {
        HttpResponse creationResponse = createStudent("pablo", "5");
        String[] resource = creationResponse.getLastHeader("Location").getValue().split("/");
        String id = resource[resource.length-1];

        HttpClient client = HttpClients.createDefault();
        URI uri = new URIBuilder().setPath(Web.STUDENTS + "/" + id)
                                          .setParameter("id", id)
                                          .build();
        HttpGet get = new HttpGet(uri);
        HttpResponse response = client.execute(get);
        assertEquals(Web.SUCCESS, response.getStatusLine().getStatusCode());

        InputStream input = response.getEntity().getContent();
        String json = IOUtils.toString(input, "UTF-8");
        System.out.println(json);
        Student student = new Gson().fromJson(json, Student.class);
        assertEquals(Integer.parseInt(id), student.getId());
        assertEquals("pablo", student.getName());
     }

}