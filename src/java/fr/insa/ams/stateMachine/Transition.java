package fr.insa.ams.stateMachine;

public class Transition {

    Transitionable initialState;
    Transitionable finalState;
    Transitionator event;

    public Transition(Transitionable initialState, Transitionator event, Transitionable finalState) {
        this.initialState = initialState;
        this.event = event;
        this.finalState = finalState;
    }

    public Transitionable getInitialState() {
        return this.initialState;
    }

    public Transitionator getEvent() {
        return this.event;
    }

    public Transitionable getFinalState() {
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
