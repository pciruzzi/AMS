package fr.insa.ams;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Database {

    public SessionFactory factory;

    public Database() {
        try {
            factory = new Configuration().configure("fr/insa/ams/hibernate/hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public int addActor(Actor actor) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer actorID = null;
        try {
            tx = session.beginTransaction();
            actorID = (Integer) session.save(actor);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return actorID;
    }

    public int addApplication(Application application) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer applicationID = null;
        try {
            tx = session.beginTransaction();
            applicationID = (Integer) session.save(application);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return applicationID;
    }

}
