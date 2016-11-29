package fr.insa.ams;

public enum ApplicationState {

    WAITING_PARTNER("Waiting for the partner's approval"),
    WAITING_CC("Waiting for the class coordinator's approval"),
    REFUSED_PARTNER("Refused by the partner"),
    REFUSED_CC("Refused by the class coordinator"),
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
