package fr.insa.ams;

import fr.insa.ams.stateMachine.ApplicationEvent;

public class FSD extends Actor {

    public FSD() {}
    public FSD(String password, String email, Group group) {
        super("FSD", password, email, group);
    }

    @Override
    public ApplicationEvent getApplicationEvent(boolean accept) {
        return accept ? ApplicationEvent.FSD_APPROVE : ApplicationEvent.FSD_REFUSE;
    }

}
