package pl.polsl.school.diary.api.student;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.school.diary.api.exception.WrongRequestException;
import pl.polsl.school.diary.api.token.TokenRepository;
import pl.polsl.school.diary.api.user.User;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/students")
@AllArgsConstructor
public class StudentController {

    private final TokenRepository tokenRepository;

    @GetMapping(value = "/{id}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentView getStudent(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                                  @PathVariable Long id) {
        User student = tokenRepository.getAndValidateUserFromHeader(tokenHeader, id);
        if(!(student instanceof Student))
            throw new WrongRequestException("This request is designed for students");
        return new StudentView((Student) student);
    }

}
