package pl.polsl.school.diary.api.issue.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class IssueMessageView {

    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(required = true, example = "With response to your message...", position = 1)
    private String message;

    @ApiModelProperty(required = true, position = 2)
    private Long issueId;

    @ApiModelProperty(required = true, position = 3)
    private Long authorId;

    public IssueMessageView(IssueMessage issueMessage){
        this(issueMessage.getId(),
                issueMessage.getMessage(),
                issueMessage.getIssue().getId(),
                issueMessage.getAuthor().getId());
    }

}