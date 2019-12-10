package pl.polsl.school.diary.api.grade;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.school.diary.api.base.Message;
import pl.polsl.school.diary.api.exception.*;
import pl.polsl.school.diary.api.parent.Parent;
import pl.polsl.school.diary.api.student.Student;
import pl.polsl.school.diary.api.teacher.Teacher;
import pl.polsl.school.diary.api.token.TokenRepository;
import pl.polsl.school.diary.api.user.User;
import pl.polsl.school.diary.api.user.UserRepository;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/grades")
@AllArgsConstructor
public class GradeController {

    private final TokenRepository tokenRepository;
    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GradeView addGrade(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                              @RequestBody GradePost gradePost) {
        User teacher = tokenRepository.getUserFromHeader(tokenHeader);
        if (!(teacher instanceof Teacher))
            throw new NotAuthorizedActionException("only teacher can add grades");
        Long studentId = gradePost.getStudentId();
        Short gradeValue = gradePost.getValue();
        if (studentId == null || gradeValue == null)
            throw new EmptyRequestBodyException();
        if (gradeValue < 1 || gradeValue > 6)
            throw new WrongRequestBodyException("Grade value has to be number between 1 and 6");
        User student = userRepository.findById(studentId).orElseThrow(() -> new NotFoundException(studentId));
        if (!(student instanceof Student))
            throw new WrongRequestBodyException("field studentId has to point on Student");
        Grade grade = new Grade();
        grade.setStudent((Student) student);
        grade.setTeacher((Teacher) teacher);
        grade.setValue(gradeValue);
        grade.setSubject(((Teacher) teacher).getTaughtSubject());
        return new GradeView(gradeRepository.save(grade));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<GradeView> getGrades(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                                    @RequestParam(required = false, defaultValue = "-1") Long studentId,
                                    @RequestParam(required = false, defaultValue = "-1") Long subjectId) {
        User user = tokenRepository.getUserFromHeader(tokenHeader);
        Set<GradeProjection> projections = null;

        if (user instanceof Student)
            projections = gradeRepository.findStudentGrades(user.getId(), subjectId);
        else if (user instanceof Teacher)
            projections = gradeRepository.findStudentGradesByTeacher(studentId, subjectId, user.getId());
        else if (user instanceof Parent)
            projections = gradeRepository.findStudentGradesOfParent(studentId, subjectId, user.getId());
        if(projections == null)
            throw new NotImplementedException("this request only handles parent, student and teacher");
        return projections.stream().map(GradeView::new).collect(Collectors.toSet());
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Message modifyGrade(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                              @PathVariable Long id,
                              @RequestBody GradePatch gradePatch) {
        User teacher = tokenRepository.getUserFromHeader(tokenHeader);
        Grade grade = gradeRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        if (!(teacher instanceof Teacher) || !grade.getTeacher().getId().equals(teacher.getId()))
            throw new NotAuthorizedActionException("only teacher that gave this grade can modify it");
        if(gradePatch.getValue() != null)
            grade.setValue(gradePatch.getValue());
        gradeRepository.save(grade);
        return new Message("Success", "Grade changed successfully");
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Message deleteGrade(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                               @PathVariable Long id) {
        User teacher = tokenRepository.getUserFromHeader(tokenHeader);
        Grade grade = gradeRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        if (!(teacher instanceof Teacher) || !grade.getTeacher().getId().equals(teacher.getId()))
            throw new NotAuthorizedActionException("only teacher that gave this grade can modify it");
        gradeRepository.delete(grade);
        return new Message("Success", "Grade deleted successfully");
    }

}

