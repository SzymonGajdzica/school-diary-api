package pl.polsl.school.diary.api.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
