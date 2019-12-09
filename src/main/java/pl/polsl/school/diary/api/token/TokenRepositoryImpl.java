package pl.polsl.school.diary.api.token;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import pl.polsl.school.diary.api.authentication.AuthenticationUtils;
import pl.polsl.school.diary.api.exception.NotAuthorizedActionException;
import pl.polsl.school.diary.api.exception.NotFoundException;
import pl.polsl.school.diary.api.user.User;
import pl.polsl.school.diary.api.user.UserRepository;

@AllArgsConstructor
@Component
public class TokenRepositoryImpl implements TokenRepository {

    private final UserRepository userRepository;
    private final AuthenticationUtils authenticationUtils;

    @Override
    public @NonNull User getUserFromHeader(String tokenHeader) throws NotAuthorizedActionException {
        String userName = getUsernameFromHeader(tokenHeader);
        return userRepository.findByUsername(userName).orElseThrow(() -> new NotAuthorizedActionException("token does not match any user"));
    }

    @Override
    public @NonNull User getAndValidateUserFromHeader(String tokenHeader, Long userId) throws NotAuthorizedActionException, NotFoundException {
        String userName = getUsernameFromHeader(tokenHeader);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
        if(!userName.equals(user.getUsername()))
            throw new NotAuthorizedActionException("cannot perform actions on not your own data");
        return user;
    }

    private String getUsernameFromHeader(String tokenHeader) throws NotAuthorizedActionException {
        try {
            String token = authenticationUtils.getTokenFromHeader(tokenHeader);
            return authenticationUtils.getClaimFromToken(token, Claims::getSubject);
        }catch (Exception e){
            throw new NotAuthorizedActionException("unknown error");
        }
    }

}
