package fr.insa.ams.stateMachine;

public enum ApplicationState {

    WAITING_PARTNER("Waiting for the partner's approval"),
    WAITING_CC("Approved by the partner. Waiting for the class coordinator's approval"),
    WAITING_FSD("Approved by the partner and the class coordinator. Waiting for the FSD's approval"),
    REFUSED_PARTNER("Refused by the partner"),
    REFUSED_CC("Refused by the class coordinator"),
    REFUSED_FSD("Refused by the FSD"),
    CANCELLED_STUDENT("Cancelled by the student"),
    ACCEPTED("Accepted!");

    private String message;

    private ApplicationState(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
