package pl.polsl.school.diary.api.schoolclass;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.school.diary.api.exception.WrongRequestException;
import pl.polsl.school.diary.api.teacher.Teacher;
import pl.polsl.school.diary.api.token.TokenRepository;
import pl.polsl.school.diary.api.user.User;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/schoolClasses")
@AllArgsConstructor
public class SchoolClassController {

    private final TokenRepository tokenRepository;
    private final SchoolClassRepository schoolClassRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<SchoolClassView> getSchoolClasses() {
        return schoolClassRepository.findAll().stream().map(SchoolClassView::new).collect(Collectors.toSet());
    }

    @GetMapping(value = "/ledClass" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public LedSchoolClassView getLedSchoolClass(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader) {
        User teacher = tokenRepository.getUserFromHeader(tokenHeader);
        if(!(teacher instanceof Teacher))
            throw new WrongRequestException("This request is designed for teachers");
        SchoolClass ledClass = ((Teacher) teacher).getLedClass();
        if(ledClass == null)
            throw new WrongRequestException("This request requires having led class");
       return new LedSchoolClassView(ledClass);
    }

    @GetMapping(value = "/teachIn" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<TeachInSchoolClassView> getTeachInSchoolClasses(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader) {
        User teacher = tokenRepository.getUserFromHeader(tokenHeader);
        if(!(teacher instanceof Teacher))
            throw new WrongRequestException("This request is designed for teachers");
        return ((Teacher) teacher).getSchoolClasses().stream().map(TeachInSchoolClassView::new).collect(Collectors.toSet());
    }

}
