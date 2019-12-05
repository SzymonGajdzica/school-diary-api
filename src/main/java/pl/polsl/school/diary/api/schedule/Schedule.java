package pl.polsl.school.diary.api.schedule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.polsl.school.diary.api.base.BaseModel;
import pl.polsl.school.diary.api.schoolclass.SchoolClass;
import pl.polsl.school.diary.api.subject.Subject;
import pl.polsl.school.diary.api.teacher.Teacher;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "schedules")
@NoArgsConstructor
public class Schedule extends BaseModel {

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Classroom classroom;

    @ManyToOne
    private SchoolClass schoolClass;

}
