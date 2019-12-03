package pl.polsl.school.diary.api.exception;

public class UsernameAlreadyUsedException extends RuntimeException {

    public UsernameAlreadyUsedException(String userName){
        super("User with name '" + userName + "' already exists");
    }

}
