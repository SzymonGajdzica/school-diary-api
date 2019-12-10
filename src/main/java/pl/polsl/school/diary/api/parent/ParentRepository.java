package pl.polsl.school.diary.api.parent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ParentRepository extends JpaRepository<Parent, Long> {



}
