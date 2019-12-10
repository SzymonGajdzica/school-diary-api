package pl.polsl.school.diary.api.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
