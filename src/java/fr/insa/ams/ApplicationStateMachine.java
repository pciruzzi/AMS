package fr.insa.ams;

import java.util.HashSet;
import java.util.Set;

public class ApplicationStateMachine {

//    ApplicationState transitionMap[][] = {
//                                     //    Waiting partner                      Waiting CC
//        {
//        /*Partner approve*/    ApplicationState.WAITING_CC,  ApplicationState.WAITING_CC
//        }, {
//        /*Partner refuse*/      ApplicationState.REFUSED_PARTNER, ApplicationState.WAITING_CC
//        }
//    };
    private static Set<Transition> transitions;

    static {
        transitions = new HashSet<Transition>();
        transitions.add(new Transition(ApplicationState.WAITING_PARTNER, ApplicationEvent.PARTNER_APPROVE, ApplicationState.WAITING_CC));
        transitions.add(new Transition(ApplicationState.WAITING_PARTNER, ApplicationEvent.PARTNER_REFUSE, ApplicationState.REFUSED_PARTNER));
        transitions.add(new Transition(ApplicationState.WAITING_PARTNER, ApplicationEvent.STUDENT_REFUSE, ApplicationState.CANCELLED_STUDENT));
        transitions.add(new Transition(ApplicationState.WAITING_CC, ApplicationEvent.CC_APPROVE, ApplicationState.ACCEPTED));
        transitions.add(new Transition(ApplicationState.WAITING_CC, ApplicationEvent.CC_REFUSE, ApplicationState.REFUSED_CC));
        transitions.add(new Transition(ApplicationState.WAITING_CC, ApplicationEvent.STUDENT_REFUSE, ApplicationState.CANCELLED_STUDENT));
    }

    public ApplicationStateMachine() {
    }

//    public ApplicationState makeTransition(ApplicationState initialState, ApplicationEvent event) {
//        System.out.println("Accessing to: (" + initialState.ordinal() + "," + event.ordinal() + ")");
//        return transitionMap[event.ordinal()][initialState.ordinal()];
//    }

    public ApplicationState makeTransition(ApplicationState initialState, ApplicationEvent event) {
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
