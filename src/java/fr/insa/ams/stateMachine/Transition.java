package fr.insa.ams.stateMachine;

public class Transition {

    ApplicationState initialState;
    ApplicationState finalState;
    ApplicationEvent event;

    public Transition(ApplicationState initialState, ApplicationEvent event, ApplicationState finalState) {
        this.initialState = initialState;
        this.event = event;
        this.finalState = finalState;
    }

    public ApplicationState getInitialState() {
        return this.initialState;
    }

    public ApplicationEvent getEvent() {
        return this.event;
    }

    public ApplicationState getFinalState() {
        return this.finalState;
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Transition)) return false;
        if (obj == this) return true;
        Transition transition = (Transition) obj;
        return (this.getInitialState() == transition.getInitialState() && this.getEvent() == transition.getEvent());
    }

    @Override
    public int hashCode() {
        return this.initialState.hashCode() + this.event.hashCode();
    }
}
