package pl.polsl.school.diary.api.schoolclass;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.polsl.school.diary.api.base.BaseModel;
import pl.polsl.school.diary.api.schedule.Schedule;
import pl.polsl.school.diary.api.student.Student;
import pl.polsl.school.diary.api.teacher.Teacher;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "classes")
public class SchoolClass extends BaseModel {

    @Column(name = "symbol")
    private String symbol;

    @OneToMany(mappedBy = "schoolClass")
    private Set<Student> students;

    @ManyToMany
    private Set<Teacher> teachers;

    @OneToOne
    private Teacher leadingTeacher;

    @OneToMany(mappedBy = "schoolClass")
    private Set<Schedule> schedules;
}
