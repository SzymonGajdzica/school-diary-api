package pl.polsl.school.diary.api.teacher;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.polsl.school.diary.api.activeuser.ActiveUser;
import pl.polsl.school.diary.api.grade.column.GradeColumn;
import pl.polsl.school.diary.api.note.Note;
import pl.polsl.school.diary.api.schedule.Schedule;
import pl.polsl.school.diary.api.schoolclass.SchoolClass;
import pl.polsl.school.diary.api.subject.Subject;
import pl.polsl.school.diary.api.user.User;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
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
    private Set<GradeColumn> gradeColumns;

    @OneToMany(mappedBy = "teacher")
    private Set<Note> notes;

    public Teacher(User user) {
        super(user);
    }

}
