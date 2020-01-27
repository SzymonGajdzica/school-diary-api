package pl.polsl.school.diary.api.parent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.polsl.school.diary.api.activeuser.ActiveUser;
import pl.polsl.school.diary.api.student.Student;
import pl.polsl.school.diary.api.user.User;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
@Entity
public class Parent extends ActiveUser {

    @OneToMany(mappedBy = "parent")
    private Set<Student> children = new HashSet<>();

    public Parent(User user) {
        super(user);
    }
}
