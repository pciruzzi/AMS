package fr.insa.ams;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.insa.ams.json.AdministratorAdapter;
import fr.insa.ams.json.ApplicationAdapter;
import fr.insa.ams.json.CVAdapter;
import fr.insa.ams.json.ClassCoordinatorAdapter;
import fr.insa.ams.json.FSDAdapter;
import fr.insa.ams.json.GroupAdapter;
import fr.insa.ams.json.InternshipAgreementAdapter;
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
                                      .registerTypeAdapter(InternshipAgreement.class, new InternshipAgreementAdapter())
                                      .create();

        Group group = new Group("STUDENT");
        String groupName = db.addGroup(group);
        System.out.println("Group " + group.getName() + " created with id " + groupName);

        Group group1 = db.getGroup("STUDENT");
        System.out.println("Group1 " + group1.getName() + " got from DB");

        Group group2 = new Group("STUDENT");
        groupName = db.addGroup(group2);
        System.out.println("Group2 " + group2.getName() + " created with id " + groupName);


        Student student = new Student("jp", "a@a.com", 5, "IR", "asd", "33769379998", group);
        Integer actorId = db.add(student);
        Login login = new Login(actorId, "password1", group);
        db.add(login);
        db.addCV(new CV("cv de jp"), actorId);
        CV cv = new CV("otro cv de jp");
        cv.setIsAvailable(false); //Delete...
        db.addCV(cv, actorId);

        System.out.println("Student created with id " + actorId);

        student = new Student("pepe", "a@a.com", 4, "IR", "asd", "33769379998", group);
        actorId = db.add(student);
        login = new Login(actorId, "password2", group);
        db.add(login);
        System.out.println("Student created with id " + actorId);

        group = new Group("PARTNER");
        db.addGroup(group);
        Partner partner = new Partner("Airbus", "a@a.com", "INSA", "1144370513", "Toulouse", group);
        actorId = db.add(partner);
        login = new Login(actorId, "password3", group);
        db.add(login);
        System.out.println("Partner created with id " + actorId);

        group = new Group("CLASS_COORDINATOR");
        db.addGroup(group);
        ClassCoordinator coordinator = new ClassCoordinator("Pierre", "a@a.com", 5, "IR", group);
        actorId = db.add(coordinator);
        login = new Login(actorId, "password4", group);
        db.add(login);
        System.out.println("ClassCoordinator created with id " + actorId);

        Application application = new Application(student, coordinator, partner, 45, null, "My name is pepe");
        Integer appId = db.add(application);
        System.out.println("Application created with id " + appId);

        System.out.println(gson.toJson(application));
        System.out.println("\n####################################################\n");


        group = new Group("STUDENT");
        db.addGroup(group);
        student = new Student("pablo", "a@a.com", 5, "IL", "asd", "33769379998", group);
        actorId = db.add(student);
        login = new Login(actorId, "password5", group);
        db.add(login);
        cv = new CV("cv de pablo");
        db.addCV(cv, actorId);
        System.out.println("Student created with id " + actorId);

        group = new Group("FSD");
        db.addGroup(group);
        FSD fsd = new FSD("a@a.com", group);
        actorId = db.add(fsd);
        login = new Login(actorId, "password6", group);
        db.add(login);
        System.out.println("FSD created with id " + actorId);

        application = new Application(student, coordinator, partner, 46, cv, "");
        appId = db.add(application);
        System.out.println("Application created with id " + appId);

        System.out.println(gson.toJson(application));
        System.out.println("\n####################################################\n");

        group = new Group("PARTNER");
        db.addGroup(group);
        partner = new Partner("Sogeti_France", "a@a.com", "INSA", "1144370513", "Toulouse", group);
        actorId = db.add(partner);
        login = new Login(actorId, "password7", group);
        db.add(login);
        System.out.println("Partner created with id " + actorId);

        partner = new Partner("Sopra_Steria", "a@a.com", "INSA", "1144370513", "Toulouse", group);
        actorId = db.add(partner);
        login = new Login(actorId, "password8", group);
        db.add(login);
        System.out.println("Partner created with id " + actorId);


        InternshipAgreement agreement = new InternshipAgreement(application);
        Integer agreementId = db.add(agreement);
        System.out.println("Internship agreement created with id " + agreementId);
        try {
            agreement.generatePdf("/home/pablo");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println(gson.toJson(agreement));
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

        for (int i = 1; i <= actorId; i++) {
            List<InternshipAgreement> agreements = db.getInternshipAgreements(i);
            System.out.println("Internship Agreements of actor with id=" + i);
            System.out.println(gson.toJson(agreements));
        }
        System.out.println("\n####################################################\n");
    }
}
