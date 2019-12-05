package pl.polsl.school.diary.api.exception;

public class EmptyRequestBodyException extends RuntimeException {
    public EmptyRequestBodyException(String message) {
        super("Empty request body - " + message);
    }
}
