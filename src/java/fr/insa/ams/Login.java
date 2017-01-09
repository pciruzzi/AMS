package fr.insa.ams;

public class Login implements Databasable {

    private String password;
    private int id;
    private Group group;

    public Login() {}
    public Login(int id, String password, Group group) {
        this.password = password;
        this.id = id;
        this.group = group;
    }

    public String getPassword() { return this.password;}
    public int getId() { return this.id;}
    public Group getGroup() { return this.group;}

    public void setPassword(String password) { this.password = password;}
    public void setId(int id) { this.id = id;}
    public void setGroup(Group group) { this.group = group;}
}
