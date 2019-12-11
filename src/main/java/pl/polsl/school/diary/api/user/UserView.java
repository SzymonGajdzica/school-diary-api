package pl.polsl.school.diary.api.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class UserView {

    @ApiModelProperty(required = true, example = "0")
    private Long id;

    @ApiModelProperty(required = true, example = "John", position = 1)
    private String name;

    @ApiModelProperty(required = true, example = "Bosh", position = 2)
    private String surname;

    @ApiModelProperty(required = true, example = "John33@gmail.com", position = 3)
    private String email;

    @ApiModelProperty(required = true, example = "John33", position = 4)
    private String username;

    @ApiModelProperty(required = true, position = 5)
    private Long roleId;

    public UserView(User user){
        this(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getUsername(), user.getRole().getId());
    }

}
