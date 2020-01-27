package pl.polsl.school.diary.api.schoolclass;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.polsl.school.diary.api.base.BaseModel;
import pl.polsl.school.diary.api.grade.column.GradeColumn;
import pl.polsl.school.diary.api.schedule.Schedule;
import pl.polsl.school.diary.api.student.Student;
import pl.polsl.school.diary.api.teacher.Teacher;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "classes")
public class SchoolClass extends BaseModel {

    @Column(name = "symbol")
    private String symbol;

    @OneToMany(mappedBy = "schoolClass")
    private Set<Student> students = new HashSet<>();

    @ManyToMany
    private Set<Teacher> teachers = new HashSet<>();

    @OneToOne(mappedBy = "ledClass")
    private Teacher leadingTeacher;

    @OneToMany(mappedBy = "schoolClass")
    private Set<Schedule> schedules = new HashSet<>();

    @OneToMany(mappedBy = "schoolClass")
    private Set<GradeColumn> gradeColumns = new HashSet<>();

}
