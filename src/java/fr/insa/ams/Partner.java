package fr.insa.ams;

public class Partner extends Actor {

    private String address;
    private String telephone;

    public Partner() {}
    public Partner(String name, String address, String telephone) {
        super(name);
        this.address = address;
        this.telephone = telephone;
    }

    public String getAddress() { return this.address;}
    public String getTelephone() { return this.telephone;}
    public void setAddress(String address) { this.address = address;}
    public void setTelephone(String telephone) { this.telephone = telephone;}
}
