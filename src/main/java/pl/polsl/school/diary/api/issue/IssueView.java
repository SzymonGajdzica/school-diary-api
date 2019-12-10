package pl.polsl.school.diary.api.issue;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.polsl.school.diary.api.base.BaseModel;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@ToString
@AllArgsConstructor
public class IssueView {

    @ApiModelProperty(required = true, example = "0")
    private Long id;

    @ApiModelProperty(required = true, example = "Reprehensible behaviour on school trip")
    private String topic;

    @ApiModelProperty(required = true)
    private Set<Long> messagesIds;

    @ApiModelProperty(required = true)
    private Set<Long> membersIds;

    public IssueView(Issue issue){
        this(issue.getId(),
                issue.getTopic(),
                issue.getMembers().stream().map(BaseModel::getId).collect(Collectors.toSet()),
                issue.getMembers().stream().map(BaseModel::getId).collect(Collectors.toSet()));
    }

}
