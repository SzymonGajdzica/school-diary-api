package pl.polsl.school.diary.api.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class ErrorMessage {

    @ApiModelProperty(required = true, example = "NotAuthorizedException")
    @Setter(AccessLevel.NONE)
    private String title;

    @ApiModelProperty(required = true, example = "Not allowed to execute this request", position = 1)
    @Setter(AccessLevel.NONE)
    private String description;

}
