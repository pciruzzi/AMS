package fr.insa.ams.stateMachine;

import org.junit.After;
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

    @After
    public void tearDown() {
    }

    @Test
    public void shouldMakeTransition() {
        state = ApplicationStateMachine.makeTransition(state, ApplicationEvent.PARTNER_APPROVE);
        assertEquals(ApplicationState.WAITING_CC, state);
    }

    @Test
    public void ifTransitionDoesntExistShouldReturnSameState() {
        state = ApplicationStateMachine.makeTransition(state, ApplicationEvent.CC_APPROVE);
        assertEquals(ApplicationState.WAITING_PARTNER, state);
    }

    @Test
    public void studentShouldBeAbleToFinishTheApplicationProcess() {
        state = ApplicationStateMachine.makeTransition(state, ApplicationEvent.PARTNER_APPROVE);
        assertEquals(ApplicationState.WAITING_CC, state);
        state = ApplicationStateMachine.makeTransition(state, ApplicationEvent.CC_APPROVE);
        assertEquals(ApplicationState.WAITING_FSD, state);
        state = ApplicationStateMachine.makeTransition(state, ApplicationEvent.FSD_APPROVE);
        assertEquals(ApplicationState.ACCEPTED, state);
    }

    @Test
    public void studentShouldBeAbleToCancelWhenWaitingForPartner() {
        state = ApplicationStateMachine.makeTransition(state, ApplicationEvent.STUDENT_REFUSE);
        assertEquals(ApplicationState.CANCELLED_STUDENT, state);
    }

    @Test
    public void studentShouldBeAbleToCancelWhenWaitingForCoordinator() {
        state = ApplicationStateMachine.makeTransition(state, ApplicationEvent.PARTNER_APPROVE);
        state = ApplicationStateMachine.makeTransition(state, ApplicationEvent.STUDENT_REFUSE);
        assertEquals(ApplicationState.CANCELLED_STUDENT, state);
    }

    @Test
    public void studentShouldBeAbleToCancelWhenWaitingForFSD() {
        state = ApplicationStateMachine.makeTransition(state, ApplicationEvent.PARTNER_APPROVE);
        state = ApplicationStateMachine.makeTransition(state, ApplicationEvent.CC_APPROVE);
        state = ApplicationStateMachine.makeTransition(state, ApplicationEvent.STUDENT_REFUSE);
        assertEquals(ApplicationState.CANCELLED_STUDENT, state);
    }

}