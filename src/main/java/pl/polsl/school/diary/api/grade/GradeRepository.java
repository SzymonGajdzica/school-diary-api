package pl.polsl.school.diary.api.grade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface GradeRepository extends JpaRepository<Grade, Long> {

    Set<Grade> findAllByStudentParentId(Long parentId);

    Set<Grade> findAllByStudentId(Long studentId);

    Set<Grade> findAllByStudentParentIdAndGradeColumnTeacherTaughtSubjectId(Long parentId, Long subjectId);

    Set<Grade> findAllByStudentIdAndGradeColumnTeacherTaughtSubjectId(Long studentId, Long subjectId);

}
