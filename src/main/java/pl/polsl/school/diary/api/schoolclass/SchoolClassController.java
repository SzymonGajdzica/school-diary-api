package pl.polsl.school.diary.api.schoolclass;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/schoolClasses")
@AllArgsConstructor
public class SchoolClassController {

    private final SchoolClassRepository schoolClassRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<SchoolClassView> getSchoolClasses() {
        return schoolClassRepository.findAll().stream().map(SchoolClassView::new).collect(Collectors.toSet());
    }

}
