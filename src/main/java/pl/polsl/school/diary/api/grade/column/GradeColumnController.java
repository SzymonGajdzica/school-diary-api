package pl.polsl.school.diary.api.grade.column;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.school.diary.api.exception.NotAuthorizedActionException;
import pl.polsl.school.diary.api.exception.NotFoundException;
import pl.polsl.school.diary.api.exception.WrongRequestException;
import pl.polsl.school.diary.api.schoolclass.SchoolClass;
import pl.polsl.school.diary.api.schoolclass.SchoolClassRepository;
import pl.polsl.school.diary.api.teacher.Teacher;
import pl.polsl.school.diary.api.token.TokenRepository;
import pl.polsl.school.diary.api.user.User;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/columns")
@AllArgsConstructor
public class GradeColumnController {

    private final TokenRepository tokenRepository;
    private final GradeColumnRepository gradeColumnRepository;
    private final SchoolClassRepository schoolClassRepository;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GradeColumnView addGradeColumn(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                              @RequestBody GradeColumnPost gradeColumnPost) {
        User teacher = tokenRepository.getUserFromHeader(tokenHeader);
        Long schoolClassId = gradeColumnPost.getSchoolClassId();
        if (!(teacher instanceof Teacher))
            throw new NotAuthorizedActionException("only teacher can add grade column");
        SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId).orElseThrow(() -> new NotFoundException(schoolClassId));
        if(!schoolClass.getTeachers().contains(teacher))
            throw new NotAuthorizedActionException("teacher cannot add grade column to class that he is not teaching");
        GradeColumn gradeColumn = new GradeColumn();
        gradeColumn.setName(gradeColumnPost.getName());
        gradeColumn.setGrades(new HashSet<>());
        gradeColumn.setTeacher((Teacher) teacher);
        gradeColumn.setSchoolClass(schoolClass);
        return new GradeColumnView(gradeColumnRepository.save(gradeColumn));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<GradeColumnView> getGradeColumns(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                                                @RequestParam(required = false) Long schoolClassId) {
        User teacher = tokenRepository.getUserFromHeader(tokenHeader);
        if (!(teacher instanceof Teacher))
            throw new WrongRequestException("this request only handles teacher");
        Set<GradeColumn> columns;

        if (schoolClassId != null)
            columns = gradeColumnRepository.findAllByTeacherIdAndSchoolClassId(teacher.getId(), schoolClassId);
        else
            columns = gradeColumnRepository.findAllByTeacherId(teacher.getId());
        return columns.stream().map(GradeColumnView::new).collect(Collectors.toSet());
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GradeColumnView modifyGradeColumn(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                               @PathVariable Long id,
                               @RequestBody GradeColumnPatch gradeColumnPatch) {
        User teacher = tokenRepository.getUserFromHeader(tokenHeader);
        GradeColumn gradeColumn = gradeColumnRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        if (!(teacher instanceof Teacher) || !gradeColumn.getTeacher().getId().equals(teacher.getId()))
            throw new NotAuthorizedActionException("only teacher that created grade column can modify it");
        if(gradeColumnPatch.getName() != null)
            gradeColumn.setName(gradeColumnPatch.getName());
        return new GradeColumnView(gradeColumnRepository.save(gradeColumn));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteGradeColumn(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                               @PathVariable Long id) {
        User teacher = tokenRepository.getUserFromHeader(tokenHeader);
        GradeColumn gradeColumn = gradeColumnRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        if (!(teacher instanceof Teacher) || !gradeColumn.getTeacher().getId().equals(teacher.getId()))
            throw new NotAuthorizedActionException("only teacher that created grade column can delete it");
        gradeColumnRepository.delete(gradeColumn);
    }


}

