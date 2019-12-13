package pl.polsl.school.diary.api.grade.column;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.polsl.school.diary.api.base.BaseModel;
import pl.polsl.school.diary.api.grade.Grade;
import pl.polsl.school.diary.api.schoolclass.SchoolClass;
import pl.polsl.school.diary.api.teacher.Teacher;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "columns")
public class GradeColumn extends BaseModel {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "gradeColumn", cascade = CascadeType.REMOVE)
    private Set<Grade> grades;

    @ManyToOne
    private SchoolClass schoolClass;

    @ManyToOne
    private Teacher teacher;

}
