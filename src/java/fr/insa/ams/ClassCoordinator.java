package fr.insa.ams;

import fr.insa.ams.stateMachine.ApplicationEvent;
import fr.insa.ams.stateMachine.InternshipAgreementEvent;

public class ClassCoordinator extends Actor {
    
    private int year;
    private String pathway;

    public ClassCoordinator() {}
    public ClassCoordinator(String name, String password, String email, int year, String pathway, Group group) {
        super(name, password, email, group);
        this.year = year;
        this.pathway = pathway;
    }

    public int getYear() { return this.year;}
    public String getPathway() { return this.pathway;}

    public void setYear(int year) { this.year = year;}
    public void setPathway(String pathway) { this.pathway = pathway;}

    @Override
    public ApplicationEvent getApplicationEvent(boolean accept) {
        return accept ? ApplicationEvent.CC_APPROVE : ApplicationEvent.CC_REFUSE;
    }

    @Override
    public InternshipAgreementEvent getInternshipAgreementEvent(boolean accept) {
        return accept ? InternshipAgreementEvent.CC_APPROVE : InternshipAgreementEvent.CC_REFUSE;
    }

}
