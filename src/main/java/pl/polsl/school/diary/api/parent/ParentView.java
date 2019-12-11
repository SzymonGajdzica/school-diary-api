package pl.polsl.school.diary.api.parent;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.polsl.school.diary.api.student.StudentView;
import pl.polsl.school.diary.api.user.UserView;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@ToString
@AllArgsConstructor
public class ParentView {

    @ApiModelProperty(required = true)
    private UserView details;

    @ApiModelProperty(required = true, position = 1)
    private Set<StudentView> children;

    public ParentView(Parent parent){
        this(new UserView(parent), parent.getChildren().stream().map(StudentView::new).collect(Collectors.toSet()));
    }

}
