package pl.polsl.school.diary.api.issue;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import pl.polsl.school.diary.api.activeuser.ActiveUser;
import pl.polsl.school.diary.api.base.BaseModel;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "issues")
public class Issue extends BaseModel {

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
