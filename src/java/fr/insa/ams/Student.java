package fr.insa.ams;

import fr.insa.ams.stateMachine.ApplicationEvent;

public class Student extends Actor {

    private int year;
    private String pathway;
    private String address;
    private String telephone;

    public Student() {}
    public Student(String name, String password, String email, int year, String pathway, String address, String telephone) {
        super(name, password, email);
        this.year = year;
        this.pathway = pathway;
        this.address = address;
        this.telephone = telephone;
    }

    public int getYear() { return this.year;}
    public String getPathway() { return this.pathway;}
    public String getAddress() { return this.address;}
    public String getTelephone() { return this.telephone;}

    public void setYear(int year) { this.year = year;}
    public void setPathway(String pathway) { this.pathway = pathway;}
    public void setAddress(String address) { this.address = address;}
    public void setTelephone(String telephone) { this.telephone = telephone;}

    @Override
    public ApplicationEvent getApplicationEvent(boolean accept) {
        return accept ? ApplicationEvent.STUDENT_ACCEPT : ApplicationEvent.STUDENT_REFUSE;
    }

}
