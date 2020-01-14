package pl.polsl.school.diary.api.teacher;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.school.diary.api.exception.WrongRequestException;
import pl.polsl.school.diary.api.token.TokenRepository;
import pl.polsl.school.diary.api.user.User;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/teachers")
@AllArgsConstructor
public class TeacherController {

    private final TokenRepository tokenRepository;
    private final TeacherRepository teacherRepository;

    @GetMapping(value = "/details" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public TeacherView getDetails(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader) {
        User teacher = tokenRepository.getUserFromHeader(tokenHeader);
        if(!(teacher instanceof Teacher))
            throw new WrongRequestException("This request is designed for teachers");
        return new TeacherView((Teacher) teacher);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<TeacherView> getTeachers() {
        return teacherRepository.findAll().stream().map(TeacherView::new).collect(Collectors.toSet());
    }

}
