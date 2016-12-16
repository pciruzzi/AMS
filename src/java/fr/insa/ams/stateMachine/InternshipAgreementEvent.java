package fr.insa.ams.stateMachine;

public enum InternshipAgreementEvent implements Transitionator {

    PARTNER_APPROVE,
    PARTNER_REFUSE,
    CC_APPROVE,
    CC_REFUSE,
    STUDENT_APPROVE,
    STUDENT_REFUSE;

}
