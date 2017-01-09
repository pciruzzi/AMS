package fr.insa.ams;

public class CV implements Databasable {

    private String name;
    private int id;
    private boolean isAvailable;

    public CV() {}
    public CV(String name) {
        this.name = name;
        this.isAvailable = true;
    }

    public String getName() { return this.name;}
    public int getId() { return this.id;}
    public boolean getIsAvailable() { return this.isAvailable;}
    
    public void setName(String name) { this.name = name;}
    public void setId(int id) { this.id = id;}
    public void setIsAvailable(boolean isAvailable) { this.isAvailable = isAvailable;}
}
