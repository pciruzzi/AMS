package fr.insa.ams;

import fr.insa.ams.stateMachine.ApplicationEvent;
import fr.insa.ams.stateMachine.InternshipAgreementEvent;
import java.util.HashSet;
import java.util.Set;

public class Student extends Actor {

    private int year;
    private String pathway;
    private String address;
    private String telephone;
    private Set<CV> cvs;

    public Student() {}
    public Student(String name, String email, int year, String pathway, String address, String telephone, Group group) {
        super(name, email, group);
        this.year = year;
        this.pathway = pathway;
        this.address = address;
        this.telephone = telephone;
        this.cvs = new HashSet<CV>();
    }

    public int getYear() { return this.year;}
    public String getPathway() { return this.pathway;}
    public String getAddress() { return this.address;}
    public String getTelephone() { return this.telephone;}
    public Set<CV> getCvs() { return this.cvs;}

    public void setYear(int year) { this.year = year;}
    public void setPathway(String pathway) { this.pathway = pathway;}
    public void setAddress(String address) { this.address = address;}
    public void setTelephone(String telephone) { this.telephone = telephone;}
    public void setCvs(Set<CV> cvs) { this.cvs = cvs;}

    public void addCV(CV cv) {
        this.cvs.add(cv);
    }

    @Override
    public ApplicationEvent getApplicationEvent(boolean accept) {
        return accept ? ApplicationEvent.STUDENT_ACCEPT : ApplicationEvent.STUDENT_REFUSE;
    }

    @Override
    public InternshipAgreementEvent getInternshipAgreementEvent(boolean accept) {
        return accept ? InternshipAgreementEvent.STUDENT_APPROVE : InternshipAgreementEvent.STUDENT_REFUSE;
    }

}
