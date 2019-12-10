package pl.polsl.school.diary.api.grade;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class GradeView {

    @ApiModelProperty(required = true, example = "0")
    private Long id;

    @ApiModelProperty(required = true, example = "5", position = 1)
    private Short value;

    @ApiModelProperty(required = true, example = "0", position = 2)
    private Long subjectId;

    @ApiModelProperty(required = true, example = "0", position = 3)
    private Long teacherId;

    @ApiModelProperty(required = true, example = "0", position = 4)
    private Long studentId;

    public GradeView(Grade grade) {
        this(grade.getId(), grade.getValue(), grade.getSubject().getId(), grade.getTeacher().getId(), grade.getStudent().getId());
    }

    public GradeView(GradeProjection gradeProjection){
        this(gradeProjection.getId(), gradeProjection.getValue(), gradeProjection.getSubjectId(), gradeProjection.getTeacherId(), gradeProjection.getStudentId());
    }

}
