package fr.insa.ams;

public abstract class Actor implements Databaseable {

//    private static int counter = 0;
    protected int id;
    protected String name;

    public Actor() {}
    public Actor(String name) {
//        this.id = counter;
//        counter++;
        this.name = name;
    }

    public int getId() { return this.id;}
    public String getName() { return this.name;}
    public void setId(int id) { this.id = id;}
    public void setName(String name) { this.name = name;}
}
