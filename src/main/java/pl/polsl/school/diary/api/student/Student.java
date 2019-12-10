package pl.polsl.school.diary.api.student;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.polsl.school.diary.api.grade.Grade;
import pl.polsl.school.diary.api.parent.Parent;
import pl.polsl.school.diary.api.schoolclass.SchoolClass;
import pl.polsl.school.diary.api.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@EqualsAndHashCode(of = ("id"), callSuper = false)
@Data
@NoArgsConstructor
@ToString
@Entity
public class Student extends User {

    @Column(name = "has_account")
    private Boolean hasAccount;

    @ManyToOne
    private Parent parent;

    @ManyToOne
    private SchoolClass schoolClass;

    @OneToMany(mappedBy = "student")
    private Set<Grade> grades;

    public Student(User user) {
        super(user);
    }

}
