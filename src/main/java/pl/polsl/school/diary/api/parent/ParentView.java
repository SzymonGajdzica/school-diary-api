package pl.polsl.school.diary.api.parent;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.polsl.school.diary.api.student.Student;
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
    private Set<InnerStudentView> children;

    public ParentView(Parent parent){
        this(new UserView(parent), parent.getChildren().stream().map(InnerStudentView::new).collect(Collectors.toSet()));
    }

    @Data
    @ToString
    @AllArgsConstructor
    public static class InnerStudentView {

        @ApiModelProperty(required = true)
        private UserView details;

        @ApiModelProperty(required = true, example = "true", position = 1)
        private Boolean hasAccount;

        @ApiModelProperty(required = true, position = 2)
        private Long schoolClassId;

        public InnerStudentView(Student student){
            this(new UserView(student),
                    student.getHasAccount(),
                    student.getSchoolClass().getId());
        }

    }

}
