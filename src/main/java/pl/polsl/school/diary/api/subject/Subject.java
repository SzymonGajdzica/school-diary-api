package pl.polsl.school.diary.api.subject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.polsl.school.diary.api.base.BaseModel;
import pl.polsl.school.diary.api.grade.Grade;
import pl.polsl.school.diary.api.schedule.Schedule;
import pl.polsl.school.diary.api.teacher.Teacher;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@EqualsAndHashCode(of = ("id"), callSuper = false)
@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "subjects")
public class Subject extends BaseModel {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "subject")
    private Set<Grade> grades;

    @OneToMany(mappedBy = "subject")
    private Set<Schedule> schedules;

    @OneToMany(mappedBy = "taughtSubject")
    private Set<Teacher> teachers;
}