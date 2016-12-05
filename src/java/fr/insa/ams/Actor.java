package fr.insa.ams;

import fr.insa.ams.stateMachine.ApplicationEvent;

public abstract class Actor implements Databasable {

    protected int id;
    protected String name;
    protected String password;
    protected String email;

    public Actor() {}
    public Actor(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public int getId() { return this.id;}
    public String getName() { return this.name;}
    public String getPassword() { return this.password;}
    public String getEmail() { return this.email;}
    public void setId(int id) { this.id = id;}
    public void setName(String name) { this.name = name;}
    public void setPassword(String password) { this.password = password;}
    public void setEmail(String email) { this.email = email;}

    public abstract ApplicationEvent getApplicationEvent(boolean accept);
}
