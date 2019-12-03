package pl.polsl.school.diary.api.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Long id) {
        super("Could not find item with id = " + id);
    }
}
