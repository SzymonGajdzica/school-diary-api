package pl.polsl.school.diary.api.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.polsl.school.diary.api.activeuser.ActiveUser;

import java.util.List;

@RepositoryRestResource
public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findAllByMembers(ActiveUser member);

}
