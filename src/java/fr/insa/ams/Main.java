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


        Student student = new Student("Aurélien Laine", "aurelien.aine@etud.insa-toulouse.fr", 5, "IR", "143 avenue de Rangueil 31600 Muret", "33769379998", group);
        Integer actorId = db.add(student);
        Login login = new Login(actorId, "password", group);
        db.add(login);
        db.addCV(new CV("premier cv"), actorId);
        CV cv = new CV("deuxieme cv");
        cv.setIsAvailable(true); //Delete...
        db.addCV(cv, actorId);

        System.out.println("Student created with id " + actorId);

        student = new Student("Nicolas Lanore", "nicolas.lanore@etud.insa-toulouse.fr", 5, "IR", "135 avenue de Rangueil ", "33610966077", group);
        actorId = db.add(student);
        login = new Login(actorId, "password", group);
        db.add(login);
        db.addCV(new CV("premier cv"), actorId);
        db.addCV(new CV("deuxieme cv"), actorId);
        System.out.println("Student created with id " + actorId);

        group = new Group("CLASS_COORDINATOR");
        db.addGroup(group);
        ClassCoordinator coordinator = new ClassCoordinator("Maitre Pierre Murez", "pierre.murez.ps4@etud.insa-toulouse.fr", 5, "IR", group);
        actorId = db.add(coordinator);
        login = new Login(actorId, "password", group);
        db.add(login);
        System.out.println("ClassCoordinator created with id " + actorId);

        //Application application = new Application(student, coordinator, partner, 45, null, "My name is pepe");
        //Integer appId = db.add(application);
        //System.out.println("Application created with id " + appId);

        //System.out.println(gson.toJson(application));
        System.out.println("\n####################################################\n");


        group = new Group("STUDENT");
        db.addGroup(group);
        student = new Student("Thomas Ravioli", "thomas.ravioli@etud.insa-toulouse.fr", 5, "IR", "15 rue de la Fantaisie Finale 31416 Tourcoing", "33612346183", group);
        actorId = db.add(student);
        login = new Login(actorId, "password", group);
        db.add(login);
        cv = new CV("premier cv");
        db.addCV(cv, actorId);
        System.out.println("Student created with id " + actorId);

        group = new Group("FSD");
        db.addGroup(group);
        FSD fsd = new FSD("fsd@insa-toulouse.fr", group);
        actorId = db.add(fsd);
        login = new Login(actorId, "password", group);
        db.add(login);
        System.out.println("FSD created with id " + actorId);

        //System.out.println(gson.toJson(application));
        System.out.println("\n####################################################\n");

        group = new Group("PARTNER");
        db.addGroup(group);
        Partner partner = new Partner("Sogeti_France", "sogeti.france@sogeti.fr", "15 rue des Lilas 31430 Toulouse", "1144370513", "Toulouse", group);
        actorId = db.add(partner);
        login = new Login(actorId, "password", group);
        db.add(login);
        System.out.println("Partner created with id " + actorId);

        partner = new Partner("Sopra_Steria", "sopra.steria@sopra.fr", "21 rue des Tournesol 31900 Toulouse", "1144370513", "Toulouse", group);
        actorId = db.add(partner);
        login = new Login(actorId, "password", group);
        db.add(login);
        System.out.println("Partner created with id " + actorId);


        /*InternshipAgreement agreement = new InternshipAgreement(application);
        Integer agreementId = db.add(agreement);
        System.out.println("Internship agreement created with id " + agreementId);
        agreement.setState(InternshipAgreementState.ACCEPTED);
        db.update(agreement);
        System.out.println("Internship agreement updated to state " + InternshipAgreementState.ACCEPTED.name());
        try {
            agreement.generatePdf("/home/remi/test");
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
        System.out.println("\n####################################################\n");*/
    }
}
