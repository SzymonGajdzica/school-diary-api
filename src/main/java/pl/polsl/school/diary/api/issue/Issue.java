package pl.polsl.school.diary.api.issue;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import pl.polsl.school.diary.api.activeuser.ActiveUser;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "issues")
@ToString
@Data
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "start_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startDate;

    @OneToMany(mappedBy = "issue")
    private Set<IssueMessage> messages;

    @ManyToMany
    private Set<ActiveUser> members;
}
