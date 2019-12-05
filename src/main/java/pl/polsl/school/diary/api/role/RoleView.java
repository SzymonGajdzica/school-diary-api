package pl.polsl.school.diary.api.role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleView {
    private Long id;

    private String name;

    public RoleView(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
