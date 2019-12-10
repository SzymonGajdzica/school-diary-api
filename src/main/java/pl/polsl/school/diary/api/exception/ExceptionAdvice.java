package pl.polsl.school.diary.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.polsl.school.diary.api.base.Message;

@ControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Message notFoundHandler(NotFoundException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(NotAuthorizedActionException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Message notAuthorizedActionHandler(NotAuthorizedActionException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(UsernameAlreadyUsedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Message usernameAlreadyUsedHandler(UsernameAlreadyUsedException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(WrongRequestBodyException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Message wrongRequestBodyHandler(WrongRequestBodyException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(EmptyRequestBodyException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Message emptyRequestBodyHandler(EmptyRequestBodyException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(NotImplementedException.class)
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    public Message notImplementedHandler(NotImplementedException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(WrongRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Message wrongRequestHandler(WrongRequestException e) {
        return generateBasicMessage(e);
    }

    private Message generateBasicMessage(Exception e){
        return new Message(e.getClass().getSimpleName(), e.getMessage());
    }


}
