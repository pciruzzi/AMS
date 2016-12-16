package fr.insa.ams.stateMachine;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class InternshipAgreementStateMachineTest {

    private InternshipAgreementState state;

    public InternshipAgreementStateMachineTest() {
    }

    @Before
    public void setUp() {
        state = InternshipAgreementState.WAITING_STUDENT;
    }

    @Test
    public void studentShouldBeAbleToFinishTheInternshipAgreementProcess() {
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.STUDENT_APPROVE);
        assertEquals(InternshipAgreementState.WAITING_CC, state);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.CC_APPROVE);
        assertEquals(InternshipAgreementState.WAITING_PARTNER, state);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.PARTNER_APPROVE);
        assertEquals(InternshipAgreementState.ACCEPTED, state);
    }

    @Test
    public void studentShouldBeAbleToFinishTheInternshipAgreementProcessWhenCCRefuseOnce() {
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.STUDENT_APPROVE);
        assertEquals(InternshipAgreementState.WAITING_CC, state);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.CC_REFUSE);
        assertEquals(InternshipAgreementState.WAITING_STUDENT_REFUSED_CC, state);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.STUDENT_APPROVE);
        assertEquals(InternshipAgreementState.WAITING_CC, state);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.CC_APPROVE);
        assertEquals(InternshipAgreementState.WAITING_PARTNER, state);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.PARTNER_APPROVE);
        assertEquals(InternshipAgreementState.ACCEPTED, state);
    }

    @Test
    public void studentShouldBeAbleToFinishTheInternshipAgreementProcessWhenPartnerRefuseOnce() {
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.STUDENT_APPROVE);
        assertEquals(InternshipAgreementState.WAITING_CC, state);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.CC_APPROVE);
        assertEquals(InternshipAgreementState.WAITING_PARTNER, state);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.PARTNER_REFUSE);
        assertEquals(InternshipAgreementState.WAITING_STUDENT_REFUSED_PARTNER, state);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.STUDENT_APPROVE);
        assertEquals(InternshipAgreementState.WAITING_CC, state);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.CC_APPROVE);
        assertEquals(InternshipAgreementState.WAITING_PARTNER, state);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.PARTNER_APPROVE);
        assertEquals(InternshipAgreementState.ACCEPTED, state);
    }

    @Test
    public void studentShouldBeAbleToCancelWhenWaitingForStudent() {
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.STUDENT_REFUSE);
        assertEquals(InternshipAgreementState.CANCELLED_STUDENT, state);
    }

    @Test
    public void studentShouldBeAbleToCancelWhenWaitingForCC() {
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.STUDENT_APPROVE);
        assertEquals(InternshipAgreementState.WAITING_CC, state);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.STUDENT_REFUSE);
        assertEquals(InternshipAgreementState.CANCELLED_STUDENT, state);
    }

    @Test
    public void studentShouldBeAbleToCancelWhenWaitingForPartner() {
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.STUDENT_APPROVE);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.CC_APPROVE);
        assertEquals(InternshipAgreementState.WAITING_PARTNER, state);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.STUDENT_REFUSE);
        assertEquals(InternshipAgreementState.CANCELLED_STUDENT, state);
    }

    @Test
    public void studentShouldBeAbleToCancelWhenWaitingForStudentAfterCCRefuse() {
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.STUDENT_APPROVE);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.CC_REFUSE);
        assertEquals(InternshipAgreementState.WAITING_STUDENT_REFUSED_CC, state);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.STUDENT_REFUSE);
        assertEquals(InternshipAgreementState.CANCELLED_STUDENT, state);
    }

    @Test
    public void studentShouldBeAbleToCancelWhenWaitingForStudentAfterPartnerRefuse() {
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.STUDENT_APPROVE);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.CC_APPROVE);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.PARTNER_REFUSE);
        assertEquals(InternshipAgreementState.WAITING_STUDENT_REFUSED_PARTNER, state);
        state = StateMachine.makeInternshipAgreementTransition(state, InternshipAgreementEvent.STUDENT_REFUSE);
        assertEquals(InternshipAgreementState.CANCELLED_STUDENT, state);
    }

}