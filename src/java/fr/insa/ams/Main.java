package fr.insa.ams;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.insa.ams.json.AdministratorAdapter;
import fr.insa.ams.json.ApplicationAdapter;
import fr.insa.ams.json.CVAdapter;
import fr.insa.ams.json.ClassCoordinatorAdapter;
import fr.insa.ams.json.FSDAdapter;
import fr.insa.ams.json.GroupAdapter;
import fr.insa.ams.json.PartnerAdapter;
import fr.insa.ams.json.StudentAdapter;
import java.util.List;

public class Main {

    public static void main(String args[]) {
        Database db = new Database();

//        String[] schemas = HibernateUtil.getSchemas();
//        for (String schema : schemas) {
//            System.out.println(schema);
//        }

        GsonBuilder builder= new GsonBuilder();
        Gson gson = builder.registerTypeAdapter(Student.class, new StudentAdapter())
                                      .registerTypeAdapter(ClassCoordinator.class, new ClassCoordinatorAdapter())
                                      .registerTypeAdapter(Partner.class, new PartnerAdapter())
                                      .registerTypeAdapter(FSD.class, new FSDAdapter())
                                      .registerTypeAdapter(Administrator.class, new AdministratorAdapter())
                                      .registerTypeAdapter(CV.class, new CVAdapter())
                                      .registerTypeAdapter(Group.class, new GroupAdapter())
                                      .registerTypeAdapter(Application.class, new ApplicationAdapter())
                                      .create();

        Group group = new Group("group1");
        String groupName = db.addGroup(group);
        System.out.println("Group " + group.getName() + " created with id " + groupName);

        Group group1 = db.getGroup("group1");
        System.out.println("Group " + group1.getName() + " got from DB");

        Group group2 = new Group("group1");
        groupName = db.addGroup(group2);
        System.out.println("Group2 " + group2.getName() + " created with id " + groupName);


        Student student = new Student("jp", "password", "a@a.com", 4, "IR", "asd", "33769379998", group);
        Integer actorId = db.add(student);
        db.addCV(new CV("cv de jp"), actorId);
        db.addCV(new CV("otro cv de jp"), actorId);
        System.out.println("Student created with id " + actorId);

        student = new Student("pepe", "password", "a@a.com", 4, "IR", "asd", "33769379998", group);
        actorId = db.add(student);
        System.out.println("Student created with id " + actorId);

        Partner partner = new Partner("Airbus", "password", "a@a.com", "INSA", "1144370513", "Toulouse", group);
        actorId = db.add(partner);
        System.out.println("Partner created with id " + actorId);

        ClassCoordinator coordinator = new ClassCoordinator("Pierre", "password", "a@a.com", 5, "IR", group);
        actorId = db.add(coordinator);
        System.out.println("ClassCoordinator created with id " + actorId);

        Application application = new Application(student, coordinator, partner, 45);
        Integer appId = db.add(application);
        System.out.println("Application created with id " + appId);

        System.out.println(gson.toJson(application));
        System.out.println("\n####################################################\n");


        student = new Student("pablo", "password", "a@a.com", 5, "IL", "asd", "33769379998", group);
        actorId = db.add(student);
        db.addCV(new CV("cv de pablo"), actorId);
        System.out.println("Student created with id " + actorId);

        application = new Application(student, coordinator, partner, 46);
        appId = db.add(application);
        System.out.println("Application created with id " + appId);

        System.out.println(gson.toJson(application));
        System.out.println("\n####################################################\n");


        for (int i = 1; i <= actorId; i++) {
            Actor actor = db.getActor(i);
            System.out.println("Actor with id=" + i + " name=" + actor.getName());
            System.out.println(gson.toJson(actor));
        }
        System.out.println("\n####################################################\n");


        for (int i = 1; i <= actorId; i++) {
            List<Application> applications = db.getApplications(i);
            System.out.println("Applications of actor with id=" + i);
            System.out.println(gson.toJson(applications));
        }
        System.out.println("\n####################################################\n");
    }
}
