package pl.polsl.school.diary.api.grade;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.polsl.school.diary.api.grade.column.GradeColumn;

@Data
@ToString
@AllArgsConstructor
public class GradeView {

    @ApiModelProperty(required = true, example = "0")
    private Long id;

    @ApiModelProperty(required = true, example = "5", position = 1)
    private Short value;

    @ApiModelProperty(required = true, example = "0", position = 2)
    private GradeColumnViewOfGrade gradeColumn;

    @ApiModelProperty(required = true, example = "0", position = 3)
    private Long studentId;

    public GradeView(Grade grade) {
        this(grade.getId(), grade.getValue(), new GradeColumnViewOfGrade(grade.getGradeColumn()), grade.getStudent().getId());
    }

    @Data
    @ToString
    @AllArgsConstructor
    public static class GradeColumnViewOfGrade {

        @ApiModelProperty(required = true, example = "0")
        private Long id;

        @ApiModelProperty(required = true, example = "Exam1", position = 1)
        private String name;

        @ApiModelProperty(required = true, example = "0", position = 2)
        private Long teacherId;

        public GradeColumnViewOfGrade(GradeColumn gradeColumn) {
            this(gradeColumn.getId(),
                    gradeColumn.getName(),
                    gradeColumn.getTeacher().getId());
        }

    }

}
