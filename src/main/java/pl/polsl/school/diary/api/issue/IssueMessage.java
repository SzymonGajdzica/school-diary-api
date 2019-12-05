package pl.polsl.school.diary.api.issue;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.polsl.school.diary.api.activeuser.ActiveUser;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
@Data
public class IssueMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date;

    @ManyToOne
    private ActiveUser author;

    @ManyToOne
    private Issue issue;
}
