package pl.polsl.school.diary.api.schoolclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {



}
