package pl.polsl.school.diary.api.role;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;
import pl.polsl.school.diary.api.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@ToString
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    protected Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<User> users;

}