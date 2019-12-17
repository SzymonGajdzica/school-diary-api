package pl.polsl.school.diary.api.schedule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import pl.polsl.school.diary.api.base.BaseModel;
import pl.polsl.school.diary.api.classroom.Classroom;
import pl.polsl.school.diary.api.schoolclass.SchoolClass;
import pl.polsl.school.diary.api.teacher.Teacher;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "schedules")
public class Schedule extends BaseModel {

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Classroom classroom;

    @ManyToOne
    private SchoolClass schoolClass;

    @Column(name = "day")
    private Short day;

    @Column(name = "start_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime;

    @Column(name = "end_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime;

}
