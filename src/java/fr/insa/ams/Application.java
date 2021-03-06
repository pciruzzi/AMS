package fr.insa.ams;

import fr.insa.ams.stateMachine.ApplicationState;

public class Application implements Databasable {

    private int id;
    private Student student;
    private ClassCoordinator coordinator;
    private Partner partner;
    private ApplicationState state;
    private CV cv;
    private int offerID;
    private String coverLetter;

    public Application() {}
    public Application(Student student, ClassCoordinator coordinator, Partner partner, int offerID, CV cv, String coverLetter) {
        this.student = student;
        this.coordinator = coordinator;
        this.partner = partner;
        this.state = ApplicationState.WAITING_PARTNER;
        this.offerID = offerID;
        this.cv = cv;
        this.coverLetter = coverLetter;
    }

    public int getId() { return this.id;}
    public Student getStudent() { return this.student;}
    public ClassCoordinator getCoordinator() { return this.coordinator;}
    public Partner getPartner() { return this.partner;}
    public ApplicationState getState() { return this.state;}
    public int getOfferID() { return this.offerID;}
    public CV getCv() { return this.cv;}
    public String getCoverLetter() { return this.coverLetter;}

    public void setId(int id) { this.id = id;}
    public void setStudent(Student student) { this.student = student;}
    public void setCoordinator(ClassCoordinator coordinator) { this.coordinator = coordinator;}
    public void setPartner(Partner partner) { this.partner = partner;}
    public void setState(ApplicationState state) { this.state = state;}
    public void setOfferID(int offerID) { this.offerID = offerID;}
    public void setCv(CV cv) { this.cv = cv;}
    public void setCoverLetter(String coverLetter) { this.coverLetter = coverLetter;}
}
