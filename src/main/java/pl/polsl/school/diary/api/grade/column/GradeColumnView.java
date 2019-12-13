package pl.polsl.school.diary.api.grade.column;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.polsl.school.diary.api.grade.Grade;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@ToString
@AllArgsConstructor
public class GradeColumnView {

    @ApiModelProperty(required = true, example = "0")
    private Long id;

    @ApiModelProperty(required = true, example = "Exam1", position = 1)
    private String name;

    @ApiModelProperty(required = true, example = "0", position = 2)
    private Long schoolClassId;

    @ApiModelProperty(required = true, position = 3)
    private Set<GradeViewOfGradeColumn> grades;

    public GradeColumnView(GradeColumn gradeColumn) {
        this(gradeColumn.getId(),
                gradeColumn.getName(),
                gradeColumn.getSchoolClass().getId(),
                gradeColumn.getGrades().stream().map(GradeViewOfGradeColumn::new).collect(Collectors.toSet()));
    }

    @Data
    @ToString
    @AllArgsConstructor
    public static class GradeViewOfGradeColumn {

        @ApiModelProperty(required = true, example = "0")
        private Long id;

        @ApiModelProperty(required = true, example = "5", position = 1)
        private Short value;

        @ApiModelProperty(required = true, example = "0", position = 2)
        private Long studentId;

        public GradeViewOfGradeColumn(Grade grade) {
            this(grade.getId(), grade.getValue(), grade.getStudent().getId());
        }
    }

}
