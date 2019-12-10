package pl.polsl.school.diary.api.issue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@ToString
@NoArgsConstructor
public class IssuePost {

    @ApiModelProperty(required = true, example = "Reprehensible behaviour on school trip")
    private String topic;

    @ApiModelProperty(required = true)
    private Set<Long> membersIds;
}
