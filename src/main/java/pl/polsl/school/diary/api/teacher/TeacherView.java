package pl.polsl.school.diary.api.teacher;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.polsl.school.diary.api.base.BaseModel;
import pl.polsl.school.diary.api.user.UserView;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@ToString
@AllArgsConstructor
public class TeacherView {

    @ApiModelProperty(required = true)
    private UserView details;

    @ApiModelProperty(required = true, example = "false", position = 1)
    private Boolean isHeadTeacher;

    @ApiModelProperty(required = true, position = 2)
    private Set<Long> schoolClassesId;

    @ApiModelProperty(required = true, example = "0", position = 3)
    private Long taughtSubjectId;

    @ApiModelProperty(required = true, example = "0", position = 4)
    private Long ledClassId;

    public TeacherView(Teacher teacher) {
        this(new UserView(teacher), teacher.getIsHeadTeacher(),
                teacher.getSchoolClasses().stream().map(BaseModel::getId).collect(Collectors.toSet()),
                teacher.getTaughtSubject().getId(), teacher.getLedClass().getId());
    }

}
