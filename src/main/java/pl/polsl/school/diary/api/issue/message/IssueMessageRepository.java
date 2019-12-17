package pl.polsl.school.diary.api.issue.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface IssueMessageRepository extends JpaRepository<IssueMessage, Long> {

}
