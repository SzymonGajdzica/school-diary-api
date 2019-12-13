package pl.polsl.school.diary.api.grade.column;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface GradeColumnRepository extends JpaRepository<GradeColumn, Long> {

    Set<GradeColumn> findAllByTeacherId(Long teacherId);

    Set<GradeColumn> findAllByTeacherIdAndSchoolClassId(Long teacherId, Long schoolClassId);

}
