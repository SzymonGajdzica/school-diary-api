package pl.polsl.school.diary.api.activeuser;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.polsl.school.diary.api.issue.Issue;
import pl.polsl.school.diary.api.issue.IssueMessage;
import pl.polsl.school.diary.api.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Set;

@EqualsAndHashCode(of = ("id"), callSuper = false)
@Data
@NoArgsConstructor
@ToString
@Entity
public class ActiveUser extends User {

    @OneToMany(mappedBy = "author")
    private Set<IssueMessage> messages;

    @ManyToMany
    private Set<Issue> issues;

    public ActiveUser(User user) {
        super(user);
    }

    public ActiveUser(Long id) {
        super();
        this.id = id;
    }
}
