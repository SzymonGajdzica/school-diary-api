package pl.polsl.school.diary.api.schoolclass;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.polsl.school.diary.api.user.UserView;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@ToString
@AllArgsConstructor
public class TeachInSchoolClassView {

    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(required = true, example = "18", position = 1)
    private String symbol;

    @ApiModelProperty(required = true, position = 2)
    private Set<UserView> students;

    public TeachInSchoolClassView(SchoolClass schoolClass){
        this(schoolClass.getId(),
                schoolClass.getSymbol(),
                schoolClass.getStudents().stream().map(UserView::new).collect(Collectors.toSet()));
    }

}
