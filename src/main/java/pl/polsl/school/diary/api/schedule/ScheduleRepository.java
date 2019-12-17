package pl.polsl.school.diary.api.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Set<Schedule> findAllBy();

    Set<Schedule> findAllByTeacherId(Long teacherId);

    Set<Schedule> findAllByClassroomId(Long classRoomId);

    Set<Schedule> findAllBySchoolClassId(Long schoolClassId);

    Set<Schedule> findAllBySchoolClassIdAndClassroomId(Long schoolClassId, Long classRoomId);

    Set<Schedule> findAllBySchoolClassIdAndTeacherId(Long schoolClassId, Long teacherId);

    Set<Schedule> findAllByClassroomIdAndTeacherId(Long classRoomId, Long teacherId);

    Set<Schedule> findAllBySchoolClassIdAndClassroomIdAndTeacherId(Long schoolClassId, Long classRoomId, Long teacherId);

}
