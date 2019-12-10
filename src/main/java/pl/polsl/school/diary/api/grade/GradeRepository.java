package pl.polsl.school.diary.api.grade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Query(value = "select grades.id as id, value as value, subject_id as subjectId, teacher_id as teacherId, student_id as studentId from grades inner join users on users.id = grades.student_id where parent_id = :parentId and (:studentId = -1 or student_id = :studentId) and (:subjectId = -1 or subject_id = :subjectId)", nativeQuery = true)
    Set<GradeProjection> findStudentGradesOfParent(Long studentId, Long subjectId, Long parentId);

    @Query(value = "select id as id, value as value, subject_id as subjectId, teacher_id as teacherId, student_id as studentId from grades where teacher_id = :teacherId and (:studentId = -1 or student_id = :studentId) and (:subjectId = -1 or subject_id = :subjectId)", nativeQuery = true)
    Set<GradeProjection> findStudentGradesByTeacher(Long studentId, Long subjectId, Long teacherId);

    @Query(value = "select id as id, value as value, subject_id as subjectId, teacher_id as teacherId, student_id as studentId from grades where (:studentId = -1 or student_id = :studentId) and (:subjectId = -1 or subject_id = :subjectId)", nativeQuery = true)
    Set<GradeProjection> findStudentGrades(Long studentId, Long subjectId);

}
