package pl.polsl.school.diary.api.issue;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.polsl.school.diary.api.activeuser.ActiveUser;
import pl.polsl.school.diary.api.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(of = ("id"), callSuper = false)
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
    private Date date;

    @ManyToOne
    private ActiveUser author;

    @ManyToOne
    private Issue issue;
}
