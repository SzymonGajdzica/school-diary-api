package pl.polsl.school.diary.api.grade;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.school.diary.api.exception.*;
import pl.polsl.school.diary.api.grade.column.GradeColumn;
import pl.polsl.school.diary.api.grade.column.GradeColumnRepository;
import pl.polsl.school.diary.api.parent.Parent;
import pl.polsl.school.diary.api.student.Student;
import pl.polsl.school.diary.api.teacher.Teacher;
import pl.polsl.school.diary.api.token.TokenRepository;
import pl.polsl.school.diary.api.user.User;
import pl.polsl.school.diary.api.user.UserRepository;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/grades")
@AllArgsConstructor
public class GradeController {

    private final TokenRepository tokenRepository;
    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final GradeColumnRepository gradeColumnRepository;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GradeView addGrade(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                              @RequestBody GradePost gradePost) {
        User teacher = tokenRepository.getUserFromHeader(tokenHeader);
        if (!(teacher instanceof Teacher))
            throw new NotAuthorizedActionException("only teacher can add grades");
        Long studentId = gradePost.getStudentId();
        Short gradeValue = gradePost.getValue();
        Long gradeColumnId = gradePost.getGradeColumnId();
        if (studentId == null || gradeValue == null)
            throw new EmptyRequestBodyException();
        if (gradeValue < 1 || gradeValue > 6)
            throw new WrongRequestBodyException("Grade value has to be number between 1 and 6");
        User student = userRepository.findById(studentId).orElseThrow(() -> new NotFoundException(studentId));
        if (!(student instanceof Student))
            throw new WrongRequestBodyException("field studentId has to point on Student");
        GradeColumn gradeColumn = gradeColumnRepository.findById(gradeColumnId).orElseThrow(() -> new NotFoundException(gradeColumnId));
        if(!gradeColumn.getTeacher().equals(teacher))
            throw new NotAuthorizedActionException("grade column does not bellow to this teacher");
        Grade grade = new Grade();
        grade.setStudent((Student) student);
        grade.setGradeColumn(gradeColumn);
        grade.setValue(gradeValue);
        return new GradeView(gradeRepository.save(grade));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<GradeView> getGrades(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                                    @RequestParam(required = false) Long subjectId) {
        User user = tokenRepository.getUserFromHeader(tokenHeader);
        Set<Grade> grades = null;
        if (user instanceof Student && subjectId != null)
            grades = gradeRepository.findAllByStudentIdAndGradeColumnTeacherTaughtSubjectId(user.getId(), subjectId);
        else if (user instanceof Student)
            grades = gradeRepository.findAllByStudentId(user.getId());
        else if (user instanceof Parent && subjectId != null)
            grades = gradeRepository.findAllByStudentParentIdAndGradeColumnTeacherTaughtSubjectId(user.getId(), subjectId);
        else if (user instanceof Parent)
            grades = gradeRepository.findAllByStudentParentId(user.getId());
        if(grades == null)
            throw new WrongRequestException("this request only handles parent and student");
        return grades.stream().map(GradeView::new).collect(Collectors.toSet());
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GradeView modifyGrade(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                              @PathVariable Long id,
                              @RequestBody GradePatch gradePatch) {
        User teacher = tokenRepository.getUserFromHeader(tokenHeader);
        Grade grade = gradeRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        if (!(teacher instanceof Teacher) || !grade.getGradeColumn().getTeacher().getId().equals(teacher.getId()))
            throw new NotAuthorizedActionException("only teacher that gave this grade can modify it");
        if(gradePatch.getValue() != null)
            grade.setValue(gradePatch.getValue());
        return new GradeView(gradeRepository.save(grade));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteGrade(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                               @PathVariable Long id) {
        User teacher = tokenRepository.getUserFromHeader(tokenHeader);
        Grade grade = gradeRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        if (!(teacher instanceof Teacher) || !grade.getGradeColumn().getTeacher().getId().equals(teacher.getId()))
            throw new NotAuthorizedActionException("only teacher that gave this grade can modify it");
        gradeRepository.delete(grade);
    }

}

