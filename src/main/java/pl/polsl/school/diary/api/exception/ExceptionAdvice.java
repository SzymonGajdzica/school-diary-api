package pl.polsl.school.diary.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage notFoundHandler(NotFoundException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(NotAuthorizedActionException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage notAuthorizedActionHandler(NotAuthorizedActionException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(UsernameAlreadyUsedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage usernameAlreadyUsedHandler(UsernameAlreadyUsedException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(WrongRequestBodyException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage wrongRequestBodyHandler(WrongRequestBodyException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(EmptyRequestBodyException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage emptyRequestBodyHandler(EmptyRequestBodyException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(NotImplementedException.class)
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    public ErrorMessage notImplementedHandler(NotImplementedException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(WrongRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage wrongRequestHandler(WrongRequestException e) {
        return generateBasicMessage(e);
    }

    private ErrorMessage generateBasicMessage(Exception e){
        return new ErrorMessage(e.getClass().getSimpleName(), e.getMessage());
    }


}
