package pl.polsl.school.diary.api.grade.column;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class GradeColumnPatch {

    @ApiModelProperty(example = "Exam1")
    private String name;

}
