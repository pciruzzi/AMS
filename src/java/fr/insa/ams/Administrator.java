package fr.insa.ams;

import fr.insa.ams.stateMachine.ApplicationEvent;

public class Administrator extends Actor {

    public Administrator() {}
    public Administrator(String password, String email, Group group) {
        super("admin", password, email, group);
    }

    @Override
    public ApplicationEvent getApplicationEvent(boolean accept) {
        throw new UnsupportedOperationException("Not supported");
    }

}
