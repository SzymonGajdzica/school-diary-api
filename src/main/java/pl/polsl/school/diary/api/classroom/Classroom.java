package pl.polsl.school.diary.api.classroom;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.polsl.school.diary.api.base.BaseModel;
import pl.polsl.school.diary.api.schedule.Schedule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "classrooms")
public class Classroom extends BaseModel {

    @Column(name = "symbol")
    private String symbol;

    @OneToMany(mappedBy = "classroom")
    private Set<Schedule> schedules;
}
