package pl.polsl.school.diary.api.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class RoleView {

    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(required = true, example = "Teacher", position = 1)
    private String name;

    public RoleView(Role role) {
        this(role.getId(), role.getName());
    }
}
