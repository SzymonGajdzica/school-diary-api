package pl.polsl.school.diary.api.note;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import pl.polsl.school.diary.api.base.BaseModel;
import pl.polsl.school.diary.api.student.Student;
import pl.polsl.school.diary.api.teacher.Teacher;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "notes")
public class Note extends BaseModel {

    @Column(name = "symbol")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "create_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createDate = new Date();

    @Column(name = "update_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updateDate = new Date();

    @ManyToOne
    private Student student;

    @ManyToOne
    private Teacher teacher;

}