package pl.polsl.school.diary.api.schoolclass;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.polsl.school.diary.api.grade.Grade;
import pl.polsl.school.diary.api.grade.column.GradeColumn;
import pl.polsl.school.diary.api.note.NoteView;
import pl.polsl.school.diary.api.student.Student;
import pl.polsl.school.diary.api.user.UserView;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@ToString
@AllArgsConstructor
public class LedSchoolClassView {

    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(required = true, example = "18", position = 1)
    private String symbol;

    @ApiModelProperty(required = true, position = 2)
    private Set<LedSchoolClassInnerStudentView> students;

    @ApiModelProperty(required = true, position = 3)
    private Set<LedSchoolClassInnerGradeColumnView> gradeColumns;

    public LedSchoolClassView(SchoolClass schoolClass){
        this(schoolClass.getId(),
                schoolClass.getSymbol(),
                schoolClass.getStudents().stream().map(LedSchoolClassInnerStudentView::new).collect(Collectors.toSet()),
                schoolClass.getGradeColumns().stream().map(LedSchoolClassInnerGradeColumnView::new).collect(Collectors.toSet()));
    }


    @Data
    @ToString
    @AllArgsConstructor
    public static class LedSchoolClassInnerStudentView {

        @ApiModelProperty(required = true)
        private UserView details;

        @ApiModelProperty(required = true, position = 1)
        private Boolean hasAccount;

        @ApiModelProperty(required = true, position = 2)
        private UserView parent;

        @ApiModelProperty(required = true, position = 3)
        private Set<NoteView> notes;

        public LedSchoolClassInnerStudentView(Student student){
            this(new UserView(student),
                    student.getHasAccount(),
                    new UserView(student.getParent()),
                    student.getNotes().stream().map(NoteView::new).collect(Collectors.toSet()));
        }

    }

    @Data
    @ToString
    @AllArgsConstructor
    public static class LedSchoolClassInnerGradeColumnView {

        @ApiModelProperty(required = true)
        private Long id;

        @ApiModelProperty(required = true, example = "Exam1", position = 1)
        private String name;

        @ApiModelProperty(required = true, position = 2)
        private Long subjectId;

        @ApiModelProperty(required = true, position = 3)
        private Set<LedSchoolClassInnerGradeView> grades;

        public LedSchoolClassInnerGradeColumnView(GradeColumn gradeColumn) {
            this(gradeColumn.getId(),
                    gradeColumn.getName(),
                    gradeColumn.getTeacher().getTaughtSubject().getId(),
                    gradeColumn.getGrades().stream().map(LedSchoolClassInnerGradeView::new).collect(Collectors.toSet()));
        }

        @Data
        @ToString
        @AllArgsConstructor
        public static class LedSchoolClassInnerGradeView {

            @ApiModelProperty(required = true)
            private Long id;

            @ApiModelProperty(required = true, example = "5", position = 1)
            private Short value;

            @ApiModelProperty(required = true, position = 2)
            private Long studentId;

            public LedSchoolClassInnerGradeView(Grade grade) {
                this(grade.getId(),
                        grade.getValue(),
                        grade.getStudent().getId());
            }
        }

    }

}
