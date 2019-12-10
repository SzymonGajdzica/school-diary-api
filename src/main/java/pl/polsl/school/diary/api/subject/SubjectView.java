package pl.polsl.school.diary.api.subject;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class SubjectView {

    @ApiModelProperty(required = true, example = "0")
    private Long id;

    @ApiModelProperty(required = true, example = "Math", position = 1)
    private String name;

    public SubjectView(Subject subject){
        id = subject.getId();
        name = subject.getName();
    }

}
