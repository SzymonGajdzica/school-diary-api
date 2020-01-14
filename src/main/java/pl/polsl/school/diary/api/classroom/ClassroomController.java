package pl.polsl.school.diary.api.classroom;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/classrooms")
@AllArgsConstructor
public class ClassroomController {

    private final ClassroomRepository classroomRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ClassroomView> getClassrooms() {
        return classroomRepository.findAll().stream().map(ClassroomView::new).collect(Collectors.toSet());
    }

}
