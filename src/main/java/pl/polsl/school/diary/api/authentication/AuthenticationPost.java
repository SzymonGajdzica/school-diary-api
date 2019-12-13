package pl.polsl.school.diary.api.authentication;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AuthenticationPost {

    @ApiModelProperty(required = true, example = "jano33")
    private String username;
    @ApiModelProperty(required = true, example = "jano33", position = 1)
    private String password;

}