package fr.insa.ams;

import fr.insa.ams.stateMachine.InternshipAgreementState;

public class InternshipAgreement implements Databasable {

    private int id;
    private Application application;
    private InternshipAgreementState state;

    public InternshipAgreement() {}
    public InternshipAgreement(Application application) {
        this.application = application;
        this.state = InternshipAgreementState.WAITING_STUDENT;
    }

    public int getId() { return this.id;}
    public Application getApplication() { return this.application;}
    public InternshipAgreementState getState() { return this.state;}

    public void setId(int id) { this.id = id;}
    public void setApplication(Application application) { this.application = application;}
    public void setState(InternshipAgreementState state) { this.state = state;}
}
