package fr.insa.ams.stateMachine;

import java.util.HashSet;
import java.util.Set;

public class StateMachine {

    private static Set<Transition> applicationsTransitions;
    private static Set<Transition> intershipAgreementsTransitions;

    static {
        applicationsTransitions = new HashSet<Transition>();
        applicationsTransitions.add(new Transition(ApplicationState.WAITING_PARTNER, ApplicationEvent.PARTNER_APPROVE, ApplicationState.WAITING_CC));
        applicationsTransitions.add(new Transition(ApplicationState.WAITING_PARTNER, ApplicationEvent.PARTNER_REFUSE, ApplicationState.REFUSED_PARTNER));
        applicationsTransitions.add(new Transition(ApplicationState.WAITING_PARTNER, ApplicationEvent.STUDENT_REFUSE, ApplicationState.CANCELLED_STUDENT));
        applicationsTransitions.add(new Transition(ApplicationState.WAITING_CC, ApplicationEvent.CC_APPROVE, ApplicationState.WAITING_FSD));
        applicationsTransitions.add(new Transition(ApplicationState.WAITING_CC, ApplicationEvent.CC_REFUSE, ApplicationState.REFUSED_CC));
        applicationsTransitions.add(new Transition(ApplicationState.WAITING_CC, ApplicationEvent.STUDENT_REFUSE, ApplicationState.CANCELLED_STUDENT));
        applicationsTransitions.add(new Transition(ApplicationState.WAITING_FSD, ApplicationEvent.FSD_APPROVE, ApplicationState.ACCEPTED));
        applicationsTransitions.add(new Transition(ApplicationState.WAITING_FSD, ApplicationEvent.FSD_REFUSE, ApplicationState.REFUSED_FSD));
        applicationsTransitions.add(new Transition(ApplicationState.WAITING_FSD, ApplicationEvent.STUDENT_REFUSE, ApplicationState.CANCELLED_STUDENT));

        intershipAgreementsTransitions = new HashSet<Transition>();
        intershipAgreementsTransitions.add(new Transition(InternshipAgreementState.WAITING_STUDENT, InternshipAgreementEvent.STUDENT_APPROVE, InternshipAgreementState.WAITING_CC));
        intershipAgreementsTransitions.add(new Transition(InternshipAgreementState.WAITING_STUDENT, InternshipAgreementEvent.STUDENT_REFUSE, InternshipAgreementState.CANCELLED_STUDENT));
        intershipAgreementsTransitions.add(new Transition(InternshipAgreementState.WAITING_CC, InternshipAgreementEvent.CC_APPROVE, InternshipAgreementState.WAITING_PARTNER));
        intershipAgreementsTransitions.add(new Transition(InternshipAgreementState.WAITING_CC, InternshipAgreementEvent.CC_REFUSE, InternshipAgreementState.WAITING_STUDENT_REFUSED_CC));
        intershipAgreementsTransitions.add(new Transition(InternshipAgreementState.WAITING_CC, InternshipAgreementEvent.STUDENT_REFUSE, InternshipAgreementState.CANCELLED_STUDENT));
        intershipAgreementsTransitions.add(new Transition(InternshipAgreementState.WAITING_PARTNER, InternshipAgreementEvent.PARTNER_APPROVE, InternshipAgreementState.ACCEPTED));
        intershipAgreementsTransitions.add(new Transition(InternshipAgreementState.WAITING_PARTNER, InternshipAgreementEvent.PARTNER_REFUSE, InternshipAgreementState.WAITING_STUDENT_REFUSED_PARTNER));
        intershipAgreementsTransitions.add(new Transition(InternshipAgreementState.WAITING_PARTNER, InternshipAgreementEvent.STUDENT_REFUSE, InternshipAgreementState.CANCELLED_STUDENT));
        intershipAgreementsTransitions.add(new Transition(InternshipAgreementState.WAITING_STUDENT_REFUSED_CC, InternshipAgreementEvent.STUDENT_APPROVE, InternshipAgreementState.WAITING_CC));
        intershipAgreementsTransitions.add(new Transition(InternshipAgreementState.WAITING_STUDENT_REFUSED_CC, InternshipAgreementEvent.STUDENT_REFUSE, InternshipAgreementState.CANCELLED_STUDENT));
        intershipAgreementsTransitions.add(new Transition(InternshipAgreementState.WAITING_STUDENT_REFUSED_PARTNER, InternshipAgreementEvent.STUDENT_APPROVE, InternshipAgreementState.WAITING_CC));
        intershipAgreementsTransitions.add(new Transition(InternshipAgreementState.WAITING_STUDENT_REFUSED_PARTNER, InternshipAgreementEvent.STUDENT_REFUSE, InternshipAgreementState.CANCELLED_STUDENT));
    }

    public StateMachine() {
    }

    private static Transitionable makeTransition(Transitionable initialState, Transitionator event, Set<Transition> transitions) {
        Transitionable finalState = initialState;
        for (Transition transition : transitions) {
            if (transition.getInitialState() == initialState && transition.getEvent() == event) {
                finalState = transition.getFinalState();
                break;
            }
        }
        return finalState;
    }

    public static ApplicationState makeApplicationTransition(ApplicationState initialState, ApplicationEvent event) {
        return (ApplicationState) makeTransition(initialState, event, applicationsTransitions);
    }

    public static InternshipAgreementState makeInternshipAgreementTransition(InternshipAgreementState initialState, InternshipAgreementEvent event) {
        return (InternshipAgreementState) makeTransition(initialState, event, intershipAgreementsTransitions);
    }

}
