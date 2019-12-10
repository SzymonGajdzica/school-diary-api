package pl.polsl.school.diary.api.grade;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class GradePost {

    @ApiModelProperty(required = true, example = "5", allowableValues = "1, 2, 3, 4, 5, 6")
    private Short value;

    @ApiModelProperty(required = true, example = "0", position = 1)
    private Long studentId;

}
