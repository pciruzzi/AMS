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

    public int addStudent(Student student) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer studentID = null;
        try {
            tx = session.beginTransaction();
            studentID = (Integer) session.save(student);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return studentID;
    }

    public int addPartner(Partner partner) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer partnerID = null;
        try {
            tx = session.beginTransaction();
            partnerID = (Integer) session.save(partner);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return partnerID;
    }

    public int addClassCoordinator(ClassCoordinator coordinator) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer coordinatorID = null;
        try {
            tx = session.beginTransaction();
            coordinatorID = (Integer) session.save(coordinator);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return coordinatorID;
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
