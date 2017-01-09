package fr.insa.ams;

import fr.insa.ams.stateMachine.ApplicationEvent;
import fr.insa.ams.stateMachine.InternshipAgreementEvent;

public abstract class Actor implements Databasable {

    protected int id;
    protected String name;
    protected String email;
    protected Group group;

    public Actor() {}
    public Actor(String name, String email, Group group) {
        this.name = name;
        this.email = email;
        this.group = group;
    }

    public int getId() { return this.id;}
    public String getName() { return this.name;}
    public String getEmail() { return this.email;}
    public Group getGroup() { return this.group;}

    public void setId(int id) { this.id = id;}
    public void setName(String name) { this.name = name;}
    public void setEmail(String email) { this.email = email;}
    public void setGroup(Group group) { this.group = group;}

    public abstract ApplicationEvent getApplicationEvent(boolean accept);
    public abstract InternshipAgreementEvent getInternshipAgreementEvent(boolean accept);
}
