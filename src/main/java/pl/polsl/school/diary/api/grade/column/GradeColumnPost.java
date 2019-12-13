package pl.polsl.school.diary.api.grade.column;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class GradeColumnPost {

    @ApiModelProperty(required = true, example = "Exam1")
    private String name;

    @ApiModelProperty(required = true, example = "0", position = 1)
    private Long schoolClassId;

}
