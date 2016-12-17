package fr.insa.ams;

import fr.insa.ams.stateMachine.ApplicationEvent;
import fr.insa.ams.stateMachine.InternshipAgreementEvent;

public class FSD extends Actor {

    public FSD() {}
    public FSD(String password, String email, Group group) {
        super("FSD", password, email, group);
    }

    @Override
    public ApplicationEvent getApplicationEvent(boolean accept) {
        return accept ? ApplicationEvent.FSD_APPROVE : ApplicationEvent.FSD_REFUSE;
    }

    @Override
    public InternshipAgreementEvent getInternshipAgreementEvent(boolean accept) {
        throw new UnsupportedOperationException("Not supported");
    }

}
