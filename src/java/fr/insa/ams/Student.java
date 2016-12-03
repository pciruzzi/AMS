package fr.insa.ams;

import fr.insa.ams.stateMachine.ApplicationEvent;

public class Student extends Actor {

    private int year;

    public Student() {}
    public Student(String name, int year) {
        super(name);
        this.year = year;
    }

    public int getYear() { return this.year;}
    public void setYear(int year) { this.year = year;}

    @Override
    public ApplicationEvent getApplicationEvent(boolean accept) {
        return accept ? ApplicationEvent.STUDENT_ACCEPT : ApplicationEvent.STUDENT_REFUSE;
    }

}
