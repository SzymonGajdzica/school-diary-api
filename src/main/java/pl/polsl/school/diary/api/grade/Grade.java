package pl.polsl.school.diary.api.grade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.polsl.school.diary.api.base.BaseModel;
import pl.polsl.school.diary.api.student.Student;
import pl.polsl.school.diary.api.subject.Subject;
import pl.polsl.school.diary.api.teacher.Teacher;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@Entity
@Table(name = "grades")
public class Grade extends BaseModel {

    @Min(value = 1)
    @Max(value = 6)
    @Column(name = "value")
    private Short value;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Student student;
}
