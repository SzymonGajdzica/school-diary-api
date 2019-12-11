package pl.polsl.school.diary.api.student;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.polsl.school.diary.api.user.UserView;

@Data
@ToString
@AllArgsConstructor
public class StudentView {

    @ApiModelProperty(required = true)
    private UserView details;

    @ApiModelProperty(required = true, example = "true", position = 1)
    private Boolean hasAccount;

    @ApiModelProperty(required = true, position = 2)
    private UserView parent;

    @ApiModelProperty(required = true, example = "0", position = 3)
    private Long schoolClassId;

    public StudentView(Student student){
        this(new UserView(student), student.getHasAccount(), new UserView(student.getParent()), student.getSchoolClass().getId());
    }

}
