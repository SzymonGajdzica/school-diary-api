package pl.polsl.school.diary.api.exception;

public class NotImplementedException extends RuntimeException {

    public NotImplementedException(String message) {
        super("Not implemented " + message);
    }

}
