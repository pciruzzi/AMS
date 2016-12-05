package fr.insa.ams.stateMachine;

public enum ApplicationEvent {

    PARTNER_APPROVE,
    PARTNER_REFUSE,
    CC_APPROVE,
    CC_REFUSE,
    FSD_APPROVE,
    FSD_REFUSE,
    STUDENT_REFUSE,
    STUDENT_ACCEPT; // This shouldn't be used in the StateMachine...

}
