package fr.insa.ams;

public class Group implements Databasable {

    private String name;

    public Group() {}
    public Group(String name) {
        this.name = name;
    }

    public String getName() { return this.name;}
    public void setName(String name) { this.name = name;}

}
