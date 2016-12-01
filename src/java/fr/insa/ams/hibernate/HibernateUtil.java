package fr.insa.ams.hibernate;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;

public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    private static String configurationPath = "fr/insa/ams/hibernate/hibernate.cfg.xml";
    private static Configuration configuration;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            System.out.println("HIBERNATE UTIL: Configuration path: " + configurationPath);
            System.out.println("HIBERNATE UTIL: Creating sessionFactory from MySQL configuration");
            createSessionFactory();
        }
        System.out.println("HIBERNATE UTIL: Returning session factory...");
        return sessionFactory;
    }

    public static void setSessionFactory(String configurationPath) {
        System.out.println("HIBERNATE UTIL: Setting sessionFactory from outside");
        HibernateUtil.configurationPath = configurationPath;
        createSessionFactory();
    }

    public static String[] getSchemas() {
        Dialect hibDialect = Dialect.getDialect(configuration.getProperties());
        return configuration.generateSchemaCreationScript(hibDialect);
    }

    public static void createSessionFactory() {
        try {
            configuration = new AnnotationConfiguration().configure(configurationPath);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }
}
