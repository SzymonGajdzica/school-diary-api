package pl.polsl.school.diary.api.role;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/roles")
@AllArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<RoleView> getRoles() {
        return roleRepository.findAll().stream().map(RoleView::new).collect(Collectors.toSet());
    }

}
