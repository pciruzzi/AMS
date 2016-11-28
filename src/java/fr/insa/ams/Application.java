package fr.insa.ams;

public class Application implements Databaseable {

    int id;
    Student student;
    ClassCoordinator coordinator;
    Partner partner;
    int offerID;

    public Application() {}
    public Application(Student student, ClassCoordinator coordinator, Partner partner, int offerID) {
        this.student = student;
        this.coordinator = coordinator;
        this.partner = partner;
        this.offerID = offerID;
    }

    public int getId() { return this.id;}
    public Student getStudent() { return this.student;}
    public ClassCoordinator getCoordinator() { return this.coordinator;}
    public Partner getPartner() { return this.partner;}
    public int getOfferID() { return this.offerID;}

    public void setId(int id) { this.id = id;}
    public void setStudent(Student student) { this.student = student;}
    public void setCoordinator (ClassCoordinator coordinator) { this.coordinator = coordinator;}
    public void setPartner (Partner partner) { this.partner = partner;}
    public void setOfferID(int offerID) { this.offerID = offerID;}

}
