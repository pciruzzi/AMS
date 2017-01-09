package fr.insa.ams;

import fr.insa.ams.stateMachine.ApplicationEvent;
import fr.insa.ams.stateMachine.InternshipAgreementEvent;

public class Administrator extends Actor {

    public Administrator() {}
    public Administrator(String email, Group group) {
        super("admin", email, group);
    }

    @Override
    public ApplicationEvent getApplicationEvent(boolean accept) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public InternshipAgreementEvent getInternshipAgreementEvent(boolean accept) {
        throw new UnsupportedOperationException("Not supported");
    }

}
