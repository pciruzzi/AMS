package fr.insa.ams;

import java.util.List;

public class Main {

    public static void main(String args[]) {
        Database db = new Database();

        Student student = new Student("pepe", 4);
        Integer id = db.add(student);
        System.out.println("Student created with id " + id);
        Partner partner = new Partner("juan", "INSA", "1144370513");
        id = db.add(partner);
        System.out.println("Partner created with id " + id);
        ClassCoordinator coordinator = new ClassCoordinator("carlos", 5, "IR");
        id = db.add(coordinator);
        System.out.println("ClassCoordinator created with id " + id);
        Application application = new Application(student, coordinator, partner, 45);
        id = db.add(application);
        System.out.println("Application created with id " + id);
        
        student = new Student("pablo", 4);
        id = db.add(student);
        System.out.println("Student created with id " + id);
        application = new Application(student, coordinator, partner, 45);
        id = db.add(application);
        System.out.println("Application created with id " + id);
        System.out.println("####################################################");

        for (int i=1; i <= 5; i++) {
            List<Application> applications = db.getApplications(i);
            System.out.println("Applications of actor with id=" + i);
            for (Application app : applications) {
                System.out.println("\t" + app.getId());
            }
        }
        System.out.println("####################################################");

        db.delete(application);
        for (int i=1; i <= 5; i++) {
            List<Application> applications = db.getApplications(i);
            System.out.println("Applications of actor with id=" + i + ", after deleting application");
            for (Application app : applications) {
                System.out.println("\t" + app.getId());
            }
        }
        System.out.println("####################################################");

        db.delete(student);
        for (int i=1; i <= 5; i++) {
            List<Application> applications = db.getApplications(i);
            System.out.println("Applications of actor with id=" + i + ", after deleting student");
            for (Application app : applications) {
                System.out.println("\t" + app.getId());
            }
        }
        System.out.println("####################################################");

        System.out.println("Now there should be an exception for violating FK");
        db.delete(student);
    }

}
