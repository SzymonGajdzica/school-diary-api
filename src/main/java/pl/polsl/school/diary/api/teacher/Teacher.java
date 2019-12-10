package pl.polsl.school.diary.api.teacher;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.polsl.school.diary.api.activeuser.ActiveUser;
import pl.polsl.school.diary.api.grade.Grade;
import pl.polsl.school.diary.api.schedule.Schedule;
import pl.polsl.school.diary.api.schoolclass.SchoolClass;
import pl.polsl.school.diary.api.subject.Subject;
import pl.polsl.school.diary.api.user.User;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
@Entity
public class Teacher extends ActiveUser {
    @Column(name = "is_head_teacher")
    private Boolean isHeadTeacher;

    @ManyToMany
    private Set<SchoolClass> schoolClasses;

    @ManyToOne
    private Subject taughtSubject;

    @OneToOne
    private SchoolClass ledClass;

    @OneToMany(mappedBy = "teacher")
    private Set<Schedule> schedules;

    @OneToMany(mappedBy = "teacher")
    private Set<Grade> grades;

    public Teacher(User user) {
        super(user);
    }

}
