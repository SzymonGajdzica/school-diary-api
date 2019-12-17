package pl.polsl.school.diary.api;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.polsl.school.diary.api.activeuser.ActiveUser;
import pl.polsl.school.diary.api.classroom.Classroom;
import pl.polsl.school.diary.api.classroom.ClassroomRepository;
import pl.polsl.school.diary.api.grade.Grade;
import pl.polsl.school.diary.api.grade.GradeRepository;
import pl.polsl.school.diary.api.grade.column.GradeColumn;
import pl.polsl.school.diary.api.grade.column.GradeColumnRepository;
import pl.polsl.school.diary.api.issue.Issue;
import pl.polsl.school.diary.api.issue.IssueRepository;
import pl.polsl.school.diary.api.issue.message.IssueMessage;
import pl.polsl.school.diary.api.issue.message.IssueMessageRepository;
import pl.polsl.school.diary.api.note.Note;
import pl.polsl.school.diary.api.note.NoteRepository;
import pl.polsl.school.diary.api.parent.Parent;
import pl.polsl.school.diary.api.parent.ParentRepository;
import pl.polsl.school.diary.api.role.Role;
import pl.polsl.school.diary.api.role.RoleRepository;
import pl.polsl.school.diary.api.schedule.Schedule;
import pl.polsl.school.diary.api.schedule.ScheduleRepository;
import pl.polsl.school.diary.api.schoolclass.SchoolClass;
import pl.polsl.school.diary.api.schoolclass.SchoolClassRepository;
import pl.polsl.school.diary.api.student.Student;
import pl.polsl.school.diary.api.student.StudentRepository;
import pl.polsl.school.diary.api.subject.Subject;
import pl.polsl.school.diary.api.subject.SubjectRepository;
import pl.polsl.school.diary.api.teacher.Teacher;
import pl.polsl.school.diary.api.teacher.TeacherRepository;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Configuration
public class SampleDataConfig implements ApplicationRunner {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final GradeRepository gradeRepository;
    private final IssueRepository issueRepository;
    private final IssueMessageRepository issueMessageRepository;
    private final ParentRepository parentRepository;
    private final RoleRepository roleRepository;
    private final ClassroomRepository classroomRepository;
    private final ScheduleRepository scheduleRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final GradeColumnRepository gradeColumnRepository;
    private final NoteRepository noteRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role studentRole = createStudentRole();
        Role parentRole = createParentRole();
        Role teacherRole = createTeacherRole();
        SchoolClass schoolClass = createSchoolClass();
        Classroom classroom = createClassroom();
        Subject subject = createSubject();
        Parent parent = createParent(parentRole);
        Teacher teacher = createTeacher(teacherRole, subject, schoolClass);
        GradeColumn gradeColumn = createGradeColumn(teacher, schoolClass);
        Student student = createStudent(studentRole, parent, schoolClass);
        Issue issue = createIssue(new HashSet<>(Arrays.asList(parent, teacher)));
        Schedule schedule = createSchedule(classroom, schoolClass, teacher);
        Grade grade = createGrade(student, gradeColumn);
        IssueMessage issueMessage = createIssueMessage(teacher, issue);
        Note note = createNote(teacher, student);
    }

    private Note createNote(Teacher teacher, Student student) {
        Note note = new Note();
        note.setTitle("Bad behaviour on school trip");
        note.setDescription("Jan was...");
        note.setTeacher(teacher);
        note.setStudent(student);
        return noteRepository.save(note);
    }

    private GradeColumn createGradeColumn(Teacher teacher, SchoolClass schoolClass) {
        GradeColumn gradeColumn = new GradeColumn();
        gradeColumn.setName("Test1");
        gradeColumn.setSchoolClass(schoolClass);
        gradeColumn.setTeacher(teacher);
        return gradeColumnRepository.save(gradeColumn);
    }

    private Subject createSubject() {
        Subject subject = new Subject();
        subject.setName("Math");
        return subjectRepository.save(subject);
    }

    private SchoolClass createSchoolClass() {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setSymbol("32");
        return schoolClassRepository.save(schoolClass);
    }

    private Schedule createSchedule(Classroom classroom, SchoolClass schoolClass, Teacher teacher) {
        Schedule schedule = new Schedule();
        schedule.setClassroom(classroom);
        schedule.setSchoolClass(schoolClass);
        schedule.setTeacher(teacher);
        schedule.setDay((short) 1);
        schedule.setStartTime(LocalTime.of(10, 0));
        schedule.setEndTime(LocalTime.of(10, 45));
        return scheduleRepository.save(schedule);
    }

    private Classroom createClassroom(){
        Classroom classroom = new Classroom();
        classroom.setSymbol("3D");
        return classroomRepository.save(classroom);
    }

    private Role createParentRole() {
        Role role = new Role();
        role.setName("Parent");
        return roleRepository.save(role);
    }

    private Role createStudentRole() {
        Role role = new Role();
        role.setName("Student");
        return roleRepository.save(role);
    }

    private Role createTeacherRole() {
        Role role = new Role();
        role.setName("Teacher");
        return roleRepository.save(role);
    }

    private IssueMessage createIssueMessage(ActiveUser author, Issue issue) {
        IssueMessage issueMessage = new IssueMessage();
        issueMessage.setMessage("We got some important things to talk");
        issueMessage.setAuthor(author);
        issueMessage.setIssue(issue);
        return issueMessageRepository.save(issueMessage);
    }

    private Issue createIssue(Set<ActiveUser> members) {
        Issue issue = new Issue();
        issue.setTopic("Important issue topic");
        issue.setMembers(members);
        return issueRepository.save(issue);
    }

    private Grade createGrade(Student student, GradeColumn gradeColumn){
        Grade grade = new Grade();
        grade.setValue((short) 2);
        grade.setStudent(student);
        grade.setGradeColumn(gradeColumn);
        return gradeRepository.save(grade);
    }

    private Teacher createTeacher(Role teacherRole, Subject taughtSubject, SchoolClass ledClass) {
        Teacher teacher = new Teacher();
        teacher.setSchoolClasses(new HashSet<>(Collections.singletonList(ledClass)));
        teacher.setIsHeadTeacher(true);
        teacher.setTaughtSubject(taughtSubject);
        teacher.setLedClass(ledClass);
        teacher.setUsername("jano33");
        teacher.setPassword(bCryptPasswordEncoder.encode("jano33"));
        teacher.setEmail("jano33@wp.pl");
        teacher.setName("janothree");
        teacher.setSurname("janowicz");
        teacher.setRole(teacherRole);
        teacher = teacherRepository.save(teacher);
        ledClass.setTeachers(new HashSet<>(Collections.singletonList(teacher)));
        schoolClassRepository.save(ledClass);
        return teacher;
    }

    private Parent createParent(Role parentRole) {
        Parent parent = new Parent();
        parent.setUsername("jano22");
        parent.setPassword(bCryptPasswordEncoder.encode("jano22"));
        parent.setEmail("jano22@wp.pl");
        parent.setName("janoone");
        parent.setSurname("janowicz");
        parent.setRole(parentRole);
        return parentRepository.save(parent);
    }

    private Student createStudent(Role studentRole, Parent parent, SchoolClass schoolClass) {
        Student student = new Student();
        student.setSchoolClass(schoolClass);
        student.setParent(parent);
        student.setHasAccount(true);
        student.setUsername("jano11");
        student.setPassword(bCryptPasswordEncoder.encode("jano11"));
        student.setEmail("jano11@wp.pl");
        student.setName("janotwo");
        student.setSurname("janowicz");
        student.setRole(studentRole);
        return studentRepository.save(student);
    }

}
