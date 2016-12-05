package fr.insa.ams;

import fr.insa.ams.stateMachine.ApplicationState;
import fr.insa.ams.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Database {

    public SessionFactory factory;
    public static int fsdId;

    public Database() {
        factory = HibernateUtil.getSessionFactory();
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

    public int add(FSD fsd) {
        fsdId = add((Databasable) fsd);
        return fsdId;
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
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return actor;
        }
    }

    public Actor getFSD() {
        return getActor(fsdId);
    }

    public ApplicationState getApplicationState(int id) {
        Application application = this.getApplication(id);
        return application.getState();
    }

    public Application getApplication(int id) {
        Session session = factory.openSession();
        Application application = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            application = (Application) session.get(Application.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return application;
        }
    }

    public List<Application> getApplications(int actorId) {
        Session session = factory.openSession();
        List<Application> applications = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            applications = session.createQuery("FROM Application A WHERE A.student.id=" + actorId + " OR A.partner.id=" + actorId + " OR A.coordinator.id=" + actorId).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return applications;
        }
    }

    public void updateApplication(Application application) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(application);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
