package fr.insa.ams;

public class ClassCoordinator extends Actor {
    
    private int year;
    private String pathway;

    public ClassCoordinator() {}
    public ClassCoordinator(String name, int year, String pathway) {
        super(name);
        this.year = year;
        this.pathway = pathway;
    }

    public int getYear() { return this.year;}
    public String getPathway() { return this.pathway;}

    public void setYear(int year) { this.year = year;}
    public void setPathway(String pathway) { this.pathway = pathway;}

}
