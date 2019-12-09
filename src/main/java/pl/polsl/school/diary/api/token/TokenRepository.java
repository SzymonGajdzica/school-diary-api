package pl.polsl.school.diary.api.token;

import lombok.NonNull;
import pl.polsl.school.diary.api.exception.NotAuthorizedActionException;
import pl.polsl.school.diary.api.exception.NotFoundException;
import pl.polsl.school.diary.api.user.User;

public interface TokenRepository {

    @NonNull User getUserFromHeader(String tokenHeader) throws NotAuthorizedActionException;

    @NonNull User getAndValidateUserFromHeader(String tokenHeader, Long userId) throws NotAuthorizedActionException, NotFoundException;

}
