package fr.insa.ams;

import java.util.List;
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

    public int add(Databasable entity) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer id = null;
        try {
            tx = session.beginTransaction();
            id = (Integer) session.save(entity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    public void delete(Databasable entity) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(entity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public Actor getActor(int id) {
        Session session = factory.openSession();
        Actor actor = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            actor = (Actor) session.get(Actor.class, id);
//            actor = (Actor) session.createQuery("FROM Actor A WHERE A.id=" + id).list().get(0);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return actor;
        }
    }

    public List<Application> getApplications(int id) {
        Session session = factory.openSession();
        List<Application> applications = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            applications = session.createQuery("FROM Application A WHERE A.student.id=" + id + " OR A.partner.id=" + id + " OR A.coordinator.id=" + id).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return applications;
        }
    }

}
