package fr.insa.ams.hibernate;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    private static final Configuration configuration = new AnnotationConfiguration().configure("fr/insa/ams/hibernate/hibernate.cfg.xml");

    static {
        try {
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static String[] getSchemas() {
        Dialect hibDialect = Dialect.getDialect(configuration.getProperties());
        return configuration.generateSchemaCreationScript(hibDialect);
    }
}
