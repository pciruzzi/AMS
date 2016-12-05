
package fr.insa.ams.stateMachine;

import java.util.HashSet;
import java.util.Set;

public class ApplicationStateMachine {

    private static Set<Transition> transitions;

    static {
        transitions = new HashSet<Transition>();
        transitions.add(new Transition(ApplicationState.WAITING_PARTNER, ApplicationEvent.PARTNER_APPROVE, ApplicationState.WAITING_CC));
        transitions.add(new Transition(ApplicationState.WAITING_PARTNER, ApplicationEvent.PARTNER_REFUSE, ApplicationState.REFUSED_PARTNER));
        transitions.add(new Transition(ApplicationState.WAITING_PARTNER, ApplicationEvent.STUDENT_REFUSE, ApplicationState.CANCELLED_STUDENT));
        transitions.add(new Transition(ApplicationState.WAITING_CC, ApplicationEvent.CC_APPROVE, ApplicationState.WAITING_FSD));
        transitions.add(new Transition(ApplicationState.WAITING_CC, ApplicationEvent.CC_REFUSE, ApplicationState.REFUSED_CC));
        transitions.add(new Transition(ApplicationState.WAITING_CC, ApplicationEvent.STUDENT_REFUSE, ApplicationState.CANCELLED_STUDENT));
        transitions.add(new Transition(ApplicationState.WAITING_FSD, ApplicationEvent.FSD_APPROVE, ApplicationState.ACCEPTED));
        transitions.add(new Transition(ApplicationState.WAITING_FSD, ApplicationEvent.FSD_REFUSE, ApplicationState.REFUSED_FSD));
        transitions.add(new Transition(ApplicationState.WAITING_FSD, ApplicationEvent.STUDENT_REFUSE, ApplicationState.CANCELLED_STUDENT));
    }

    public ApplicationStateMachine() {
    }

    public static ApplicationState makeTransition(ApplicationState initialState, ApplicationEvent event) {
        ApplicationState finalState = initialState;
        for (Transition transition : transitions) {
            if (transition.getInitialState() == initialState && transition.getEvent() == event) {
                finalState = transition.getFinalState();
                break;
            }
        }
        return finalState;
    }

}
