package pl.polsl.school.diary.api.issue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.polsl.school.diary.api.base.BaseModel;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@ToString
@NoArgsConstructor
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
        id = issue.getId();
        topic = issue.getTopic();
        membersIds = issue.getMembers().stream().map(BaseModel::getId).collect(Collectors.toSet());
        messagesIds = new HashSet<>();
    }

}
