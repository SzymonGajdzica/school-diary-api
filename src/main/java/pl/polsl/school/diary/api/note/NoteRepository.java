package pl.polsl.school.diary.api.note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface NoteRepository extends JpaRepository<Note, Long> {

    Set<Note> findAllByStudentId(Long studentId);

    Set<Note> findAllByStudentParentId(Long parentId);

    Set<Note> findAllByTeacherId(Long teacherId);

}
