package pl.polsl.school.diary.api.issue.message;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.school.diary.api.activeuser.ActiveUser;
import pl.polsl.school.diary.api.exception.EmptyRequestBodyException;
import pl.polsl.school.diary.api.exception.NotAuthorizedActionException;
import pl.polsl.school.diary.api.exception.NotFoundException;
import pl.polsl.school.diary.api.issue.Issue;
import pl.polsl.school.diary.api.issue.IssueRepository;
import pl.polsl.school.diary.api.token.TokenRepository;
import pl.polsl.school.diary.api.user.User;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin
@RestController
@RequestMapping(value = "/issueMessages")
@AllArgsConstructor
public class IssueMessageController {

    private final TokenRepository tokenRepository;
    private final IssueRepository issueRepository;
    private final IssueMessageRepository issueMessageRepository;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public IssueMessageView addIssueMessage(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                              @RequestBody IssueMessagePost issueMessagePost) {
        if(issueMessagePost.getIssueId() == null || issueMessagePost.getMessage() == null || issueMessagePost.getMessage().isEmpty())
            throw new EmptyRequestBodyException();

        User activeUser = tokenRepository.getUserFromHeader(tokenHeader);
        if(!(activeUser instanceof ActiveUser))
            throw new NotAuthorizedActionException("should be active user");

        Long issueId = issueMessagePost.getIssueId();
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new NotFoundException(issueId));
        if(!issue.getMembers().contains(activeUser))
            throw new NotAuthorizedActionException("this user is not added to this issue");

        IssueMessage issueMessage = new IssueMessage();
        issueMessage.setAuthor((ActiveUser) activeUser);
        issueMessage.setIssue(issue);
        issueMessage.setMessage(issueMessagePost.getMessage());
        return new IssueMessageView(issueMessageRepository.save(issueMessage));
    }
}
