package fr.insa.ams;

import fr.insa.ams.stateMachine.ApplicationEvent;

public class Administrator extends Actor {

    public Administrator() {}
    public Administrator(String name, String password, String email, Group group) {
        super(name, password, email, group);
    }

    @Override
    public ApplicationEvent getApplicationEvent(boolean accept) {
        throw new UnsupportedOperationException("Not supported");
    }

}
