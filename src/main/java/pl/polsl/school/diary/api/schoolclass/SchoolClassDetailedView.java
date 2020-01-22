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
public class SchoolClassDetailedView {

    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(required = true, example = "18", position = 1)
    private String symbol;

    @ApiModelProperty(required = true, position = 2)
    private Set<SchoolClassInnerStudentView> students;

    @ApiModelProperty(required = true, position = 3)
    private Set<SchoolClassDetailedInnerGradeColumnView> gradeColumns;

    public SchoolClassDetailedView(SchoolClass schoolClass){
        this(schoolClass.getId(),
                schoolClass.getSymbol(),
                schoolClass.getStudents().stream().map(SchoolClassInnerStudentView::new).collect(Collectors.toSet()),
                schoolClass.getGradeColumns().stream().map(SchoolClassDetailedInnerGradeColumnView::new).collect(Collectors.toSet()));
    }


    @Data
    @ToString
    @AllArgsConstructor
    public static class SchoolClassInnerStudentView {

        @ApiModelProperty(required = true)
        private UserView details;

        @ApiModelProperty(required = true, position = 1)
        private Boolean hasAccount;

        @ApiModelProperty(required = true, position = 2)
        private UserView parent;

        @ApiModelProperty(required = true, position = 3)
        private Set<NoteView> notes;

        public SchoolClassInnerStudentView(Student student){
            this(new UserView(student),
                    student.getHasAccount(),
                    new UserView(student.getParent()),
                    student.getNotes().stream().map(NoteView::new).collect(Collectors.toSet()));
        }

    }

    @Data
    @ToString
    @AllArgsConstructor
    public static class SchoolClassDetailedInnerGradeColumnView {

        @ApiModelProperty(required = true)
        private Long id;

        @ApiModelProperty(required = true, example = "Exam1", position = 1)
        private String name;

        @ApiModelProperty(required = true, position = 2)
        private Long subjectId;

        @ApiModelProperty(required = true, position = 3)
        private Set<SchoolClassDetailedInnerGradeView> grades;

        public SchoolClassDetailedInnerGradeColumnView(GradeColumn gradeColumn) {
            this(gradeColumn.getId(),
                    gradeColumn.getName(),
                    gradeColumn.getTeacher().getTaughtSubject().getId(),
                    gradeColumn.getGrades().stream().map(SchoolClassDetailedInnerGradeView::new).collect(Collectors.toSet()));
        }

        @Data
        @ToString
        @AllArgsConstructor
        public static class SchoolClassDetailedInnerGradeView {

            @ApiModelProperty(required = true)
            private Long id;

            @ApiModelProperty(required = true, example = "5", position = 1)
            private Short value;

            @ApiModelProperty(required = true, position = 2)
            private Long studentId;

            public SchoolClassDetailedInnerGradeView(Grade grade) {
                this(grade.getId(),
                        grade.getValue(),
                        grade.getStudent().getId());
            }
        }

    }

}
