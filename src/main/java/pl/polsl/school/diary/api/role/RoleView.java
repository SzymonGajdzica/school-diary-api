package pl.polsl.school.diary.api.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class RoleView {

    @ApiModelProperty(required = true, example = "0")
    private Long id;
    @ApiModelProperty(required = true, example = "Teacher")
    private String name;

    public RoleView(Role role) {
        this(role.getId(), role.getName());
    }
}
