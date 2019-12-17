package pl.polsl.school.diary.api.classroom;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class ClassroomView {

    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(required = true, example = "38a", position = 1)
    private String symbol;

    public ClassroomView(Classroom classroom) {
        this(classroom.getId(), classroom.getSymbol());
    }
}