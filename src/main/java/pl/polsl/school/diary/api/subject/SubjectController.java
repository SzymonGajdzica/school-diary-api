package pl.polsl.school.diary.api.subject;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/subjects")
@AllArgsConstructor
public class SubjectController {

    private final SubjectRepository subjectRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<SubjectView> getSubjects() {
        return subjectRepository.findAll().stream().map(SubjectView::new).collect(Collectors.toSet());
    }

}
