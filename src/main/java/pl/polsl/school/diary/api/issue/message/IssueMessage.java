package pl.polsl.school.diary.api.issue.message;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.polsl.school.diary.api.activeuser.ActiveUser;
import pl.polsl.school.diary.api.base.BaseModel;
import pl.polsl.school.diary.api.issue.Issue;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "messages")
public class IssueMessage extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date = new Date();

    @ManyToOne
    private ActiveUser author;

    @ManyToOne
    private Issue issue;
}
