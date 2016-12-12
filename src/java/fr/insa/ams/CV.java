package fr.insa.ams;

public class CV {

    private String name;
    private int id;

    public CV() {}
    public CV(String name) {
        this.name = name;
    }

    public String getName() { return this.name;}
    public int getId() { return this.id;}
    
    public void setName(String name) { this.name = name;}
    public void setId(int id) { this.id = id;}
}
