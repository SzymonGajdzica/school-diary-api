package pl.polsl.school.diary.api.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserView {

    @ApiModelProperty(required = true, example = "0")
    private Long id;
    @ApiModelProperty(required = true, example = "John")
    private String name;
    @ApiModelProperty(required = true, example = "Bosh")
    private String surname;
    @ApiModelProperty(required = true, example = "John33@gmail.com")
    private String email;
    @ApiModelProperty(required = true, example = "John33")
    private String username;
    //ApiModelProperty(required = true)
    //private Set<Long> rolesId;
    @ApiModelProperty(required = true)
    private Long roleId;

    public UserView(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.roleId = user.getRole().getId();
        //this.roleId = null;
        //if(user.getRoles() != null)
            //this.rolesId = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
        //else
            //this.rolesId = new HashSet<>();
    }

}
