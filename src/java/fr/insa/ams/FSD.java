package fr.insa.ams;

import fr.insa.ams.stateMachine.ApplicationEvent;

public class FSD extends Actor {

    public FSD() {}
    public FSD(String password, String email) {
        super("FSD", password, email);
    }

    @Override
    public ApplicationEvent getApplicationEvent(boolean accept) {
        return accept ? ApplicationEvent.FSD_APPROVE : ApplicationEvent.FSD_REFUSE;
    }

}
