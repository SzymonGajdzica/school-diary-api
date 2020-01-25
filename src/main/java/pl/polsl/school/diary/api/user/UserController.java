package pl.polsl.school.diary.api.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.school.diary.api.base.BaseModel;
import pl.polsl.school.diary.api.exception.NotAuthorizedActionException;
import pl.polsl.school.diary.api.role.RoleRepository;
import pl.polsl.school.diary.api.token.TokenRepository;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;

    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<UserView> getActiveUsers(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader) {
        User user = tokenRepository.getUserFromHeader(tokenHeader);
        Set<Long> allowedRolesIds = roleRepository
                .findAll()
                .stream()
                .filter(role -> role.getName().equals("Teacher") || role.getName().equals("Parent"))
                .map(BaseModel::getId)
                .collect(Collectors.toSet());
        if(!allowedRolesIds.contains(user.getRole().getId()))
            throw new NotAuthorizedActionException("should be active user");
        return userRepository.findAllByRoleIdIsIn(allowedRolesIds).stream().map(UserView::new).collect(Collectors.toSet());
    }

    @GetMapping(value = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView getDetails(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader) {
        return new UserView(tokenRepository.getUserFromHeader(tokenHeader));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                              @PathVariable Long id) {
        userRepository.delete(tokenRepository.getAndValidateUserFromHeader(tokenHeader, id));
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView updateUser(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                              @PathVariable Long id,
                              @RequestBody UserPatch userPatch) {
        User user = tokenRepository.getAndValidateUserFromHeader(tokenHeader, id);
        if(userPatch.getName() != null)
            user.setName(userPatch.getName());
        if(userPatch.getSurname() != null)
            user.setSurname(userPatch.getSurname());
        if(userPatch.getEmail() != null)
            user.setEmail(userPatch.getEmail());
        return new UserView(userRepository.save(user));
    }

}
