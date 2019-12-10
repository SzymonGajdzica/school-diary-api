package pl.polsl.school.diary.api.teacher;

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
@RequestMapping(value = "/teachers")
@AllArgsConstructor
public class TeacherController {

    private final TokenRepository tokenRepository;

    @GetMapping(value = "/details" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public TeacherView getDetails(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader) {
        User teacher = tokenRepository.getUserFromHeader(tokenHeader);
        if(!(teacher instanceof Teacher))
            throw new WrongRequestException("This request is designed for students");
        return new TeacherView((Teacher) teacher);
    }

}
