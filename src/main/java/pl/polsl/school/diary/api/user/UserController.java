package pl.polsl.school.diary.api.user;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.school.diary.api.base.Message;
import pl.polsl.school.diary.api.token.TokenRepository;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @GetMapping(value = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView getDetails(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader) {
        return new UserView(tokenRepository.getUserFromHeader(tokenHeader));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Message deleteUser(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                              @PathVariable Long id) {
        userRepository.delete(tokenRepository.getAndValidateUserFromHeader(tokenHeader, id));
        return new Message("Success", "User deleted");
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Message updateUser(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                              @PathVariable Long id,
                              @RequestBody UserPatch userPatch) {
        User user = tokenRepository.getAndValidateUserFromHeader(tokenHeader, id);
        if(userPatch.getName() != null)
            user.setName(userPatch.getName());
        if(userPatch.getSurname() != null)
            user.setSurname(userPatch.getSurname());
        if(userPatch.getEmail() != null)
            user.setEmail(userPatch.getEmail());
        userRepository.save(user);
        return new Message("Success", "User updated");
    }

}
