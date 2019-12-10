package pl.polsl.school.diary.api;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.polsl.school.diary.api.activeuser.ActiveUser;
import pl.polsl.school.diary.api.grade.Grade;
import pl.polsl.school.diary.api.grade.GradeRepository;
import pl.polsl.school.diary.api.issue.Issue;
import pl.polsl.school.diary.api.issue.IssueMessage;
import pl.polsl.school.diary.api.issue.IssueMessageRepository;
import pl.polsl.school.diary.api.issue.IssueRepository;
import pl.polsl.school.diary.api.parent.Parent;
import pl.polsl.school.diary.api.parent.ParentRepository;
import pl.polsl.school.diary.api.role.Role;
import pl.polsl.school.diary.api.role.RoleRepository;
import pl.polsl.school.diary.api.schedule.Classroom;
import pl.polsl.school.diary.api.schedule.ClassroomRepository;
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

import java.util.*;

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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role studentRole = createStudentRole();
        Role parentRole = createParentRole();
        Role teacherRole = createTeacherRole();
        SchoolClass schoolClass = createSchoolClass();
        Classroom classroom = createClassroom();
        Subject subject = createSubject();
        Parent parent = createParent(parentRole);
        Teacher teacher = createTeacher(teacherRole, new HashSet<>(Collections.singletonList(schoolClass)), subject, schoolClass);
        Student student = createStudent(studentRole, parent, schoolClass);
        Issue issue = createIssue(new HashSet<>(Arrays.asList(parent, teacher)));
        Schedule schedule = createSchedule(classroom, schoolClass, subject, teacher);
        Grade grade = createGrade(student, teacher, subject);
        IssueMessage issueMessage = createIssueMessage(teacher, issue);
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

    private Schedule createSchedule(Classroom classroom, SchoolClass schoolClass, Subject subject, Teacher teacher) {
        Schedule schedule = new Schedule();
        schedule.setClassroom(classroom);
        schedule.setSchoolClass(schoolClass);
        schedule.setSubject(subject);
        schedule.setTeacher(teacher);
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
        issueMessage.setDate(new Date());
        issueMessage.setMessage("We got some important things to talk");
        issueMessage.setAuthor(author);
        issueMessage.setIssue(issue);
        return issueMessageRepository.save(issueMessage);
    }

    private Issue createIssue(Set<ActiveUser> members) {
        Issue issue = new Issue();
        issue.setStartDate(new Date());
        issue.setTopic("Important issue topic");
        issue.setMembers(members);
        return issueRepository.save(issue);
    }

    private Grade createGrade(Student student, Teacher teacher, Subject subject){
        Grade grade = new Grade();
        grade.setValue(Integer.valueOf(2).shortValue());
        grade.setStudent(student);
        grade.setTeacher(teacher);
        grade.setSubject(subject);
        return gradeRepository.save(grade);
    }

    private Teacher createTeacher(Role teacherRole, Set<SchoolClass> schoolClasses, Subject taughtSubject, SchoolClass ledClass) {
        Teacher teacher = new Teacher();
        teacher.setSchoolClasses(schoolClasses);
        teacher.setIsHeadTeacher(true);
        teacher.setTaughtSubject(taughtSubject);
        teacher.setLedClass(ledClass);
        teacher.setUsername("jano33");
        teacher.setPassword(bCryptPasswordEncoder.encode("jano33"));
        teacher.setEmail("jano33@wp.pl");
        teacher.setName("janothree");
        teacher.setSurname("janowicz");
        teacher.setRole(teacherRole);
        return teacherRepository.save(teacher);
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
