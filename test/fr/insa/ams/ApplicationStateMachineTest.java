package fr.insa.ams;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ApplicationStateMachineTest {

    private ApplicationStateMachine machine;
    private ApplicationState state;

    public ApplicationStateMachineTest() {
    }

    @Before
    public void setUp() {
        machine = new ApplicationStateMachine();
        state = ApplicationState.WAITING_PARTNER;
    }

    @After
    public void tearDown() {
    }

    @Test
    public void shouldMakeTransition() {
        state = machine.makeTransition(state, ApplicationEvent.PARTNER_APPROVE);
        assertEquals(ApplicationState.WAITING_CC, state);
    }

    @Test
    public void ifTransitionDoesntExistShouldReturnSameState() {
        state = machine.makeTransition(state, ApplicationEvent.CC_APPROVE);
        assertEquals(ApplicationState.WAITING_PARTNER, state);
    }

    @Test
    public void studentShouldBeAbleToFinishTheApplicationProcess() {
        state = machine.makeTransition(state, ApplicationEvent.PARTNER_APPROVE);
        assertEquals(ApplicationState.WAITING_CC, state);
        state = machine.makeTransition(state, ApplicationEvent.CC_APPROVE);
        assertEquals(ApplicationState.ACCEPTED, state);
    }

    @Test
    public void studentShouldBeAbleToCancelWhenWaitingForPartner() {
        state = machine.makeTransition(state, ApplicationEvent.STUDENT_REFUSE);
        assertEquals(ApplicationState.CANCELLED_STUDENT, state);
    }

    @Test
    public void studentShouldBeAbleToCancelWhenWaitingForCoordinator() {
        state = machine.makeTransition(state, ApplicationEvent.PARTNER_APPROVE);
        state = machine.makeTransition(state, ApplicationEvent.STUDENT_REFUSE);
        assertEquals(ApplicationState.CANCELLED_STUDENT, state);
    }

}