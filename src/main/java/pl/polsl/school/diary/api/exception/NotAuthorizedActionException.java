package pl.polsl.school.diary.api.exception;

public class NotAuthorizedActionException extends RuntimeException {

    public NotAuthorizedActionException(String message){
        super("Not authorized - " + message);
    }

}
