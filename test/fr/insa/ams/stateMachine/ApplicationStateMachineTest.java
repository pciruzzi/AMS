package fr.insa.ams.stateMachine;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ApplicationStateMachineTest {

    private ApplicationState state;

    public ApplicationStateMachineTest() {
    }

    @Before
    public void setUp() {
        state = ApplicationState.WAITING_PARTNER;
    }

    @Test
    public void shouldMakeTransition() {
        state = StateMachine.makeApplicationTransition(state, ApplicationEvent.PARTNER_APPROVE);
        assertEquals(ApplicationState.WAITING_CC, state);
    }

    @Test
    public void ifTransitionDoesntExistShouldReturnSameState() {
        state = StateMachine.makeApplicationTransition(state, ApplicationEvent.CC_APPROVE);
        assertEquals(ApplicationState.WAITING_PARTNER, state);
    }

    @Test
    public void studentShouldBeAbleToFinishTheApplicationProcess() {
        state = StateMachine.makeApplicationTransition(state, ApplicationEvent.PARTNER_APPROVE);
        assertEquals(ApplicationState.WAITING_CC, state);
        state = StateMachine.makeApplicationTransition(state, ApplicationEvent.CC_APPROVE);
        assertEquals(ApplicationState.WAITING_FSD, state);
        state = StateMachine.makeApplicationTransition(state, ApplicationEvent.FSD_APPROVE);
        assertEquals(ApplicationState.ACCEPTED, state);
    }

    @Test
    public void studentShouldBeAbleToCancelWhenWaitingForPartner() {
        state = StateMachine.makeApplicationTransition(state, ApplicationEvent.STUDENT_REFUSE);
        assertEquals(ApplicationState.CANCELLED_STUDENT, state);
    }

    @Test
    public void studentShouldBeAbleToCancelWhenWaitingForCoordinator() {
        state = StateMachine.makeApplicationTransition(state, ApplicationEvent.PARTNER_APPROVE);
        state = StateMachine.makeApplicationTransition(state, ApplicationEvent.STUDENT_REFUSE);
        assertEquals(ApplicationState.CANCELLED_STUDENT, state);
    }

    @Test
    public void studentShouldBeAbleToCancelWhenWaitingForFSD() {
        state = StateMachine.makeApplicationTransition(state, ApplicationEvent.PARTNER_APPROVE);
        state = StateMachine.makeApplicationTransition(state, ApplicationEvent.CC_APPROVE);
        state = StateMachine.makeApplicationTransition(state, ApplicationEvent.STUDENT_REFUSE);
        assertEquals(ApplicationState.CANCELLED_STUDENT, state);
    }

}