package pl.polsl.school.diary.api.exception;

public class WrongRequestException extends RuntimeException {

    public WrongRequestException(String message){
        super(message);
    }

}
