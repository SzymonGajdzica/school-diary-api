package pl.polsl.school.diary.api.parent;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.polsl.school.diary.api.student.StudentView;

import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
@AllArgsConstructor
public class ParentView {

    @ApiModelProperty(required = true, example = "0")
    private Long id;

    @ApiModelProperty(required = true, position = 1)
    private List<StudentView> children;

    public ParentView(Parent parent){
        this(parent.getId(), parent.getChildren().stream().map(StudentView::new).collect(Collectors.toList()));
    }

}
