package fr.insa.ams;

import fr.insa.ams.stateMachine.ApplicationEvent;

public abstract class Actor implements Databasable {

    protected int id;
    protected String name;

    public Actor() {}
    public Actor(String name) {
        this.name = name;
    }

    public int getId() { return this.id;}
    public String getName() { return this.name;}
    public void setId(int id) { this.id = id;}
    public void setName(String name) { this.name = name;}

    public abstract ApplicationEvent getApplicationEvent(boolean accept);
}
