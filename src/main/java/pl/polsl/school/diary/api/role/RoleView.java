package pl.polsl.school.diary.api.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class RoleView {

    @ApiModelProperty(required = true, example = "0")
    private Long id;
    @ApiModelProperty(required = true, example = "Teacher")
    private String name;

    public RoleView(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
