package fr.insa.ams;

import fr.insa.ams.stateMachine.ApplicationEvent;
import fr.insa.ams.stateMachine.InternshipAgreementEvent;

public class Partner extends Actor {

    private String address;
    private String telephone;
    private String location;

    public Partner() {}
    public Partner(String name, String password, String email, String address, String telephone, String location, Group group) {
        super(name, password, email, group);
        this.address = address;
        this.telephone = telephone;
        this.location = location;
    }

    public String getAddress() { return this.address;}
    public String getTelephone() { return this.telephone;}
    public String getLocation() { return this.location;}

    public void setAddress(String address) { this.address = address;}
    public void setTelephone(String telephone) { this.telephone = telephone;}
    public void setLocation(String location) { this.location = location;}

    @Override
    public ApplicationEvent getApplicationEvent(boolean accept) {
        return accept ? ApplicationEvent.PARTNER_APPROVE : ApplicationEvent.PARTNER_REFUSE;
    }

    @Override
    public InternshipAgreementEvent getInternshipAgreementEvent(boolean accept) {
        return accept ? InternshipAgreementEvent.PARTNER_APPROVE : InternshipAgreementEvent.PARTNER_REFUSE;
    }
}
