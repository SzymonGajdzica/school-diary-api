package pl.polsl.school.diary.api.exception;

public class WrongRequestBodyException extends RuntimeException {

    public WrongRequestBodyException(String message) {
        super("Wrong request body - " + message);
    }

}
