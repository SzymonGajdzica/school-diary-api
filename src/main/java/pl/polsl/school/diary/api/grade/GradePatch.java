package pl.polsl.school.diary.api.grade;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class GradePatch {

    @ApiModelProperty(required = true, example = "5", allowableValues = "1, 2, 3, 4, 5, 6")
    private Short value;

}