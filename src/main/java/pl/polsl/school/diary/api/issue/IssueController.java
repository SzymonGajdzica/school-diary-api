package pl.polsl.school.diary.api.issue;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.school.diary.api.activeuser.ActiveUser;
import pl.polsl.school.diary.api.exception.EmptyRequestBodyException;
import pl.polsl.school.diary.api.exception.NotAuthorizedActionException;
import pl.polsl.school.diary.api.exception.WrongRequestBodyException;
import pl.polsl.school.diary.api.token.TokenRepository;
import pl.polsl.school.diary.api.user.User;
import pl.polsl.school.diary.api.user.UserRepository;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/issues")
@AllArgsConstructor
public class IssueController {

    private final TokenRepository tokenRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public IssueView addIssue(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader, @RequestBody IssuePost issuePost) {
        User user = tokenRepository.getUserFromHeader(tokenHeader);
        if(!(user instanceof ActiveUser))
            throw new NotAuthorizedActionException("should be active user");

        Set<Long> membersIds = issuePost.getMembersIds();
        String topic = issuePost.getTopic();
        if(membersIds  == null || membersIds.isEmpty() || topic == null || topic.isEmpty())
            throw new EmptyRequestBodyException("missing necessary issue properties");

        Set<Long> databaseMembersIds = userRepository.findIdsByIdIsIn(membersIds);
        if(!databaseMembersIds.equals(membersIds))
            throw new WrongRequestBodyException("some users with specified ids are not present in the database or they are not allowed to be chosen");
        membersIds.add(user.getId());

        Issue issue = new Issue();
        issue.setTopic(topic);
        issue.setStartDate(new Date());
        issue.setMembers(membersIds.stream().map(ActiveUser::new).collect(Collectors.toSet()));
        issue.setMessages(new HashSet<>());

        return new IssueView(issueRepository.save(issue));
    }
}
