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

    public int addStudent(String name) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer studentID = null;
        try {
            tx = session.beginTransaction();
            Student student = new Student(name);
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

}
