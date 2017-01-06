package fr.insa.ams;

import fr.insa.ams.hibernate.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Database {

    public SessionFactory factory;

    public Database() {
        factory = HibernateUtil.getSessionFactory();
        // Like this the administrator should be always the first actor
        if (! existsAdministrator()) {
            Group group = new Group("administrator");
            this.addGroup(group);
            Administrator admin = new Administrator("password", "a@a.com", group);
            this.add(admin);
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

    public Actor getActorByName(String name) {
        Session session = factory.openSession();
        List<Actor> actors = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            actors = session.createQuery("FROM Actor A WHERE A.name='" + name + "'").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return actors.size() == 1 ? actors.get(0) : null;
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

    private Actor getUnique(String className) {
        Session session = factory.openSession();
        List<Actor> actors = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            actors = session.createQuery("FROM " + className).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return actors.size() == 1 ? actors.get(0) : null;
        }
    }

    public Administrator getAdministrator() {
        return (Administrator) getUnique("Administrator");
    }

    public boolean existsAdministrator() {
        return getAdministrator() != null;
    }

    public FSD getFSD() {
        return (FSD) getUnique("FSD");
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

    public InternshipAgreement getInternshipAgreement(int id) {
        Session session = factory.openSession();
        InternshipAgreement agreement = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            agreement = (InternshipAgreement) session.get(InternshipAgreement.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return agreement;
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

    public List<InternshipAgreement> getInternshipAgreements(int actorId) {
        List<Application> applications = this.getApplications(actorId);
        List<InternshipAgreement> agreements = new ArrayList<InternshipAgreement>();
        for (Application application : applications) {
            int id = application.getId();
            agreements.add(this.getInternshipAgreement(id));
        }
        return agreements;
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

    public void update(Databasable entity) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
