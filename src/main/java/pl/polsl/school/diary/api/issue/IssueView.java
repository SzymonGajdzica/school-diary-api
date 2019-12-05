package pl.polsl.school.diary.api.issue;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class IssueView {

    private Long id;

    private String topic;

    private Set<Long> messagesIds;

    private Set<Long> membersIds;
}
