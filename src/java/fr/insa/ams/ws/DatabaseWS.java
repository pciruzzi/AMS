package fr.insa.ams.ws;

import fr.insa.ams.hibernate.HibernateUtil;
import java.net.URI;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

// ATENTION: This path is only for use in testing, in order to delete and create the database for every test!!
@Path("database")
public class DatabaseWS {
    @Context
    private UriInfo context;

    public DatabaseWS() {
    }

    @DELETE
    public Response closeSession() {
        System.out.println("DATABASE WS: Call to closeSession");
        HibernateUtil.closeSessionFactory();
        return Response.ok().build();
    }

    @PUT
    public Response openTestSession() {
        System.out.println("DATABASE WS: Call to openTestSession");
        HibernateUtil.setSessionFactory("fr/insa/ams/hibernate/hibernate-test.cfg.xml");
        return Response.created(URI.create("")).build();
    }
}
