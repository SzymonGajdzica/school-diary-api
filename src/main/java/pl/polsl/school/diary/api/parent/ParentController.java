package pl.polsl.school.diary.api.parent;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.school.diary.api.exception.WrongRequestException;
import pl.polsl.school.diary.api.token.TokenRepository;
import pl.polsl.school.diary.api.user.User;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/parents")
@AllArgsConstructor
public class ParentController {

    private final TokenRepository tokenRepository;

    @GetMapping(value = "/details" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ParentView getParent(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader) {
        User parent = tokenRepository.getUserFromHeader(tokenHeader);
        if(!(parent instanceof Parent))
            throw new WrongRequestException("This request is designed for parents");
        return new ParentView((Parent) parent);
    }

}
