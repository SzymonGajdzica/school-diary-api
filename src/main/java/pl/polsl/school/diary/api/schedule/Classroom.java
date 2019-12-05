package pl.polsl.school.diary.api.schedule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.polsl.school.diary.api.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@Entity
@Table(name = "classrooms")
public class Classroom extends BaseModel {

    @Column(name = "symbol")
    private String symbol;

    @OneToMany(mappedBy = "classroom")
    private Set<Schedule> schedules;
}
