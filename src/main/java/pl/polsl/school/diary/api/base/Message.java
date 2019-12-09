package pl.polsl.school.diary.api.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class Message {

    @ApiModelProperty(required = true, example = "Sample title")
    @Setter(AccessLevel.NONE)
    private String title;

    @ApiModelProperty(required = true, example = "Sample description")
    @Setter(AccessLevel.NONE)
    private String description;

}
