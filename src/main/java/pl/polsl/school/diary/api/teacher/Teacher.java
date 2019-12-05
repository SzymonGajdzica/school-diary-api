package pl.polsl.school.diary.api.teacher;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.polsl.school.diary.api.activeuser.ActiveUser;
import pl.polsl.school.diary.api.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@ToString
@Data
@NoArgsConstructor
public class Teacher extends ActiveUser {
    @Column(name = "is_head_teacher", nullable = false)
    private Boolean isHeadTeacher;

    public Teacher(User user) {
        super(user);
    }

}
