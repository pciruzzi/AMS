package fr.insa.ams.stateMachine;

public enum InternshipAgreementState implements Transitionable {

    WAITING_STUDENT("Waiting for the student's signature"),
    WAITING_PARTNER("Waiting for the partner's approval"),
    WAITING_CC("Approved by the partner. Waiting for the class coordinator's approval"),
    WAITING_STUDENT_REFUSED_PARTNER("The internship agreement has been accepted by the class coordinator, but refused by the partner. Waiting for the student's signature"),
    WAITING_STUDENT_REFUSED_CC("The internship agreement has been refused by the class coordinator. Waiting for the student's signature"),
    CANCELLED_STUDENT("Cancelled by the student"),
    ACCEPTED("Accepted!");

    private String message;

    private InternshipAgreementState(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
