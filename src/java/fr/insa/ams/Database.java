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
        if (existsFSD()) return -1; // Indicates that FSD already exists
        return add((Databasable) fsd);
    }

    public String addGroup(Group group) {
        Session session = factory.openSession();
        Transaction tx = null;
        String name = null;
        try {
            tx = session.beginTransaction();
            if (session.get(Group.class, group.getName()) == null) name = (String) session.save(group);
                else name = group.getName();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return name;
    }

    public int addCV(CV cv, int studentId) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer id = null;
        try {
            tx = session.beginTransaction();
            id = (Integer) session.save(cv);
            Student student = (Student) session.get(Student.class, studentId);
            student.addCV(cv);
            session.update(student);
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
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return actor;
        }
    }

    public Group getGroup(String name) {
        Session session = factory.openSession();
        Group group = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            group = (Group) session.get(Group.class, name);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return group;
        }
    }

    public FSD getFSD() {
        Session session = factory.openSession();
        List<FSD> fsds = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            fsds = session.createQuery("FROM FSD").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return fsds.size() == 1 ? fsds.get(0) : null;
        }
    }

    public boolean existsFSD() {
        return getFSD() != null;
    }

    public ClassCoordinator getCoordinator(int year, String pathway) {
        Session session = factory.openSession();
        List<ClassCoordinator> coordinators = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            coordinators = session.createQuery("FROM ClassCoordinator WHERE year=" + year + " AND pathway='" + pathway + "'").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return coordinators.size() == 1 ? coordinators.get(0) : null;
        }
    }

    public boolean existsCoordinator(int year, String pathway) {
        return getCoordinator(year, pathway) != null;
    }

    public CV getCV(int id) {
        Session session = factory.openSession();
        CV cv = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            cv = (CV) session.get(CV.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return cv;
        }
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

    public List<Application> getApplicationsByOffer(int offerId) {
        Session session = factory.openSession();
        List<Application> applications = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            applications = session.createQuery("FROM Application A WHERE A.offerID=" + offerId).list();
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
