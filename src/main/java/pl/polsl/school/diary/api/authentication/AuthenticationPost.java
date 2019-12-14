package pl.polsl.school.diary.api.authentication;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class AuthenticationPost {

    @ApiModelProperty(required = true, example = "jano33")
    private String username;
    @ApiModelProperty(required = true, example = "jano33", position = 1)
    private String password;

}