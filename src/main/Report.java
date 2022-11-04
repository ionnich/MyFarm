package main;

/**
* A report is a rich message return from a method call.
* @version 1.0
*/
public class Report {
    private String message;
    private boolean success;

    /**
     * @param success whether the action was successful
     * @param message the message to be displayed to the user
     */
    public Report(boolean success, String message) {
        this.message = message;
        this.success = success;
    }

    /**
     * @return the message embedded inside the report
     */
    public String getMessage() {
        return message;
    }


    /**
     * @return whether the action was successful
     */
    public boolean isSuccess() {
        return success;
    }
}
