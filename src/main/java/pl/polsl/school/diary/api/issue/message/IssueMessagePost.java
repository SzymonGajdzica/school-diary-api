package pl.polsl.school.diary.api.issue.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class IssueMessagePost {

    @ApiModelProperty(required = true, example = "With response to your message...")
    private String message;

    @ApiModelProperty(required = true, position = 1)
    private Long issueId;

}
