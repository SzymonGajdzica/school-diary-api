package pl.polsl.school.diary.api.grade;

import org.springframework.data.rest.core.config.Projection;

@Projection(types = Grade.class)
interface GradeProjection {

    Long getId();

    Short getValue();

    Long getSubjectId();

    Long getTeacherId();

    Long getStudentId();
}