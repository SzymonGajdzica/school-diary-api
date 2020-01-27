package pl.polsl.school.diary.api.activeuser;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.polsl.school.diary.api.issue.Issue;
import pl.polsl.school.diary.api.issue.message.IssueMessage;
import pl.polsl.school.diary.api.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
@Entity
public class ActiveUser extends User {

    @OneToMany(mappedBy = "author")
    private Set<IssueMessage> messages = new HashSet<>();

    @ManyToMany
    private Set<Issue> issues = new HashSet<>();

    public ActiveUser(User user) {
        super(user);
    }

    public ActiveUser(Long id) {
        super();
        this.id = id;
    }
}
