package fr.insa.ams;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.insa.ams.hibernate.HibernateUtil;
import fr.insa.ams.json.ActorAdapter;
import fr.insa.ams.json.ClassCoordinatorAdapter;
import fr.insa.ams.json.FSDAdapter;
import fr.insa.ams.json.PartnerAdapter;
import fr.insa.ams.json.StudentAdapter;
import java.util.List;

public class Main {

    public static void main(String args[]) {
        Database db = new Database();

        String[] schemas = HibernateUtil.getSchemas();
        for (String schema : schemas) {
            System.out.println(schema);
        }

        Group group = new Group("group1");
        String groupName = db.addGroup(group);
        System.out.println("Group " + group.getName() + " created with id " + groupName);

        Group group1 = db.getGroup("group1");
        System.out.println("Group " + group.getName() + " got from DB");
        Group group2 = new Group("group1");
        groupName = db.addGroup(group2);
        System.out.println("Group2 " + group2.getName() + " created with id " + groupName);

        Student student = new Student("pepe", "password", "a@a.com", 4, "IR", "asd", "33769379998", group);
        Integer id = db.add(student);
        System.out.println("Student created with id " + id);
        Partner partner = new Partner("juan", "password", "a@a.com", "INSA", "1144370513", "Toulouse", group);
        id = db.add(partner);
        System.out.println("Partner created with id " + id);
        ClassCoordinator coordinator = new ClassCoordinator("carlos", "password", "a@a.com", 5, "IR", group);
        id = db.add(coordinator);
        System.out.println("ClassCoordinator created with id " + id);
        Application application = new Application(student, coordinator, partner, 45);
        id = db.add(application);
        System.out.println("Application created with id " + id);
        System.out.println(new Gson().toJson(application));
        System.out.println("\n####################################################\n");

        student = new Student("pablo", "password", "a@a.com", 5, "IL", "asd", "33769379998", group);
        id = db.add(student);
        System.out.println("Student created with id " + id);
        application = new Application(student, coordinator, partner, 46);
        id = db.add(application);
        System.out.println("Application created with id " + id);
        System.out.println(new Gson().toJson(application));
        System.out.println("\n####################################################\n");

        GsonBuilder builder= new GsonBuilder();
        Gson gson = builder.registerTypeAdapter(Student.class, new StudentAdapter())
                                      .registerTypeAdapter(ClassCoordinator.class, new ClassCoordinatorAdapter())
                                      .registerTypeAdapter(Partner.class, new PartnerAdapter())
                                      .registerTypeAdapter(FSD.class, new FSDAdapter())
                                      .create();
        for (int i=1; i<=4; i++) {
            Actor actor = db.getActor(i);
            System.out.println("Actor with id=" + i + " name=" + actor.getName());
            System.out.println(gson.toJson(actor));
        }
        System.out.println("\n####################################################\n");

        for (int i=1; i <= 5; i++) {
            List<Application> applications = db.getApplications(i);
            System.out.println("Applications of actor with id=" + i);
            String result = "[\n";
            for (Application app : applications) {
                result = result.concat("\t{\n");
                result = result.concat("\t\t\"id\": " + app.getId() + ",\n");
                result = result.concat("\t\t\"idStudent\": " + app.getStudent().getId() + ",\n");
                result = result.concat("\t\t\"idPartner\": " + app.getPartner().getId() + ",\n");
                result = result.concat("\t\t\"idCoodrinator\": " + app.getCoordinator().getId() + ",\n");
                result = result.concat("\t\t\"idOffer\": " + app.getOfferID() + "\n");
                result = result.concat("\t\t\"state\": " + app.getState().getMessage() + "\n");
                result = result.concat("\t},\n");
            }
            int ind = result.lastIndexOf(',');
            if (ind >= 0) result = new StringBuilder(result).replace(ind, ind+1, "").toString();
            result = result.concat("]");
            System.out.println(result);
            // TODO: It fails here
//            System.out.println(new Gson().toJson(applications));
//            System.out.println(new JSONSerializer.serialize(applications));
        }
        System.out.println("\n####################################################\n");

//        db.delete(application);
        for (int i=1; i <= 5; i++) {
            List<Application> applications = db.getApplications(i);
            System.out.println("Applications of actor with id=" + i + ", after deleting application");
            for (Application app : applications) {
                System.out.println("\t" + app.getId());
            }
        }
        System.out.println("\n####################################################\n");

//        db.delete(student);
        for (int i=1; i <= 5; i++) {
            List<Application> applications = db.getApplications(i);
            System.out.println("Applications of actor with id=" + i + ", after deleting student");
            for (Application app : applications) {
                System.out.println("\t" + app.getId());
            }
        }
        System.out.println("\n####################################################\n");

//        System.out.println("Now there should be an exception for violating FK");
//        db.delete(student);
    }

}
