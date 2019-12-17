package pl.polsl.school.diary.api.issue;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.polsl.school.diary.api.base.BaseModel;
import pl.polsl.school.diary.api.issue.message.IssueMessage;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@ToString
@AllArgsConstructor
public class IssueView {

    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(required = true, example = "Reprehensible behaviour on school trip", position = 1)
    private String topic;

    @ApiModelProperty(required = true, position = 2)
    private Set<Long> membersIds;

    @ApiModelProperty(required = true, position = 3)
    private Set<InnerIssueMessageView> messageViews;

    public IssueView(Issue issue){
        this(issue.getId(),
                issue.getTopic(),
                issue.getMembers().stream().map(BaseModel::getId).collect(Collectors.toSet()),
                issue.getMessages().stream().map(InnerIssueMessageView::new).collect(Collectors.toSet()));
    }

    @Data
    @ToString
    @AllArgsConstructor
    public static class InnerIssueMessageView {

        @ApiModelProperty(required = true)
        private Long id;

        @ApiModelProperty(required = true, example = "With response to your message...", position = 1)
        private String message;

        @ApiModelProperty(required = true, position = 2)
        private Long authorId;

        public InnerIssueMessageView(IssueMessage issueMessage){
            this(issueMessage.getId(),
                    issueMessage.getMessage(),
                    issueMessage.getAuthor().getId());
        }

    }

}
