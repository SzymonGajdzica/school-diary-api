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
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Configuration
public class SampleDataConfig implements ApplicationRunner {

    private final Random random = new Random();
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
    public void run(ApplicationArguments args) {
        Collection<SchoolClass> schoolClasses = new HashSet<>();
        Collection<Classroom> classrooms = new HashSet<>();
        Collection<Subject> subjects = new HashSet<>();
        Collection<Parent> parents = new HashSet<>();
        Collection<Teacher> teachers = new HashSet<>();
        Collection<GradeColumn> gradeColumns = new HashSet<>();
        Collection<Student> students = new HashSet<>();
        Collection<Issue> issues = new HashSet<>();
        Collection<Schedule> schedules = new HashSet<>();
        Collection<Grade> grades = new HashSet<>();
        Collection<IssueMessage> issueMessages = new HashSet<>();
        Collection<Note> notes = new HashSet<>();

        Role studentRole = createStudentRole();
        Role parentRole = createParentRole();
        Role teacherRole = createTeacherRole();

        for (int i = 0; i < 5; i++)
            schoolClasses.add(createSchoolClass());
        for (int i = 0; i < 5; i++)
            classrooms.add(createClassroom());
        for (int i = 0; i < 5; i++)
            subjects.add(createSubject());
        for (int i = 0; i < 5; i++)
            parents.add(createParent(parentRole, i));
        for (int i = 0; i < 5; i++)
            teachers.add(createTeacher(teacherRole, subjects, schoolClasses, i));
        for (int i = 0; i < 5; i++)
            gradeColumns.add(createGradeColumn(teachers));
        for (int i = 0; i < 5; i++)
            students.add(createStudent(studentRole, parents, schoolClasses, i));
        for (int i = 0; i < 10; i++)
            issues.add(createIssue(merge(parents.stream().map(teacher1 -> (ActiveUser) teacher1).collect(Collectors.toSet()), teachers.stream().map(teacher1 -> (ActiveUser) teacher1).collect(Collectors.toSet()))));
        for (int i = 0; i < 20; i++)
            schedules.add(createSchedule(classrooms, teachers));
        for (int i = 0; i < 20; i++)
            grades.add(createGrade(gradeColumns));
        for (int i = 0; i < 20; i++)
            issueMessages.add(createIssueMessage(teachers.stream().map(teacher1 -> (ActiveUser) teacher1).collect(Collectors.toSet())));
        for (int i = 0; i < 20; i++)
            notes.add(createNote(teachers));
    }

    private Note createNote(Collection<Teacher> teachers) {
        Teacher teacher = findAny(teachers.stream().filter(teacher1 -> !teacher1.getLedClass().getStudents().isEmpty()).collect(Collectors.toSet()));
        Student student = findAny(teacher.getLedClass().getStudents());
        Note note = new Note();
        note.setTitle("Bad behaviour on school trip" + getRandom(100));
        note.setDescription("Was late " + +getRandom(100) + " times");
        note.setTeacher(teacher);
        teacher.getNotes().add(note);
        note.setStudent(student);
        student.getNotes().add(note);
        return noteRepository.save(note);
    }

    private GradeColumn createGradeColumn(Collection<Teacher> teachers) {
        Teacher teacher = findAny(teachers);
        SchoolClass schoolClass = findAny(teacher.getSchoolClasses());
        GradeColumn gradeColumn = new GradeColumn();
        gradeColumn.setName("Test " + getRandom(100));
        gradeColumn.setSchoolClass(schoolClass);
        schoolClass.getGradeColumns().add(gradeColumn);
        gradeColumn.setTeacher(teacher);
        teacher.getGradeColumns().add(gradeColumn);
        return gradeColumnRepository.save(gradeColumn);
    }

    private Subject createSubject() {
        Subject subject = new Subject();
        subject.setName("Subject " + getRandom(100));
        return subjectRepository.save(subject);
    }

    private SchoolClass createSchoolClass() {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setSymbol(getRandom(100) + "");
        return schoolClassRepository.save(schoolClass);
    }

    private Schedule createSchedule(Collection<Classroom> classrooms, Collection<Teacher> teachers) {
        Teacher teacher = findAny(teachers);
        SchoolClass schoolClass = findAny(teacher.getSchoolClasses());
        Classroom classroom = findAny(classrooms);
        Schedule schedule = new Schedule();
        schedule.setClassroom(classroom);
        classroom.getSchedules().add(schedule);
        schedule.setSchoolClass(schoolClass);
        schoolClass.getSchedules().add(schedule);
        schedule.setTeacher(teacher);
        teacher.getSchedules().add(schedule);
        schedule.setDay((short) getRandom(4));
        int hour = getRandom(8) + 8;
        schedule.setStartTime(LocalTime.of(hour, 0));
        schedule.setEndTime(LocalTime.of(hour, 45));
        return scheduleRepository.save(schedule);
    }

    private Classroom createClassroom() {
        Classroom classroom = new Classroom();
        classroom.setSymbol(getRandom(100) + "A");
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

    private IssueMessage createIssueMessage(Collection<ActiveUser> authors) {
        ActiveUser author = findAny(authors.stream().filter(activeUser -> !activeUser.getIssues().isEmpty()).collect(Collectors.toSet()));
        Issue issue = findAny(author.getIssues());
        IssueMessage issueMessage = new IssueMessage();
        issueMessage.setMessage("We got some important things to talk about issue " + getRandom(100));
        issueMessage.setAuthor(author);
        author.getMessages().add(issueMessage);
        issueMessage.setIssue(issue);
        issue.getMessages().add(issueMessage);
        return issueMessageRepository.save(issueMessage);
    }

    private Issue createIssue(Collection<ActiveUser> members) {
        Set<ActiveUser> filteredMembers = members.stream().filter(activeUser -> getRandom(100) < 50).collect(Collectors.toSet());
        Issue issue = new Issue();
        issue.setTopic("Important issue topic number " + getRandom(100));
        issue.setMembers(filteredMembers);
        filteredMembers.forEach(activeUser -> activeUser.getIssues().add(issue));
        return issueRepository.save(issue);
    }

    private Grade createGrade(Collection<GradeColumn> gradeColumns) {
        GradeColumn gradeColumn = findAny(gradeColumns.stream().filter(gradeColumn1 -> !gradeColumn1.getSchoolClass().getStudents().isEmpty()).collect(Collectors.toSet()));
        Student student = findAny(gradeColumn.getSchoolClass().getStudents());
        Grade grade = new Grade();
        grade.setValue((short) (getRandom(5) + 1));
        grade.setStudent(student);
        student.getGrades().add(grade);
        grade.setGradeColumn(gradeColumn);
        gradeColumn.getGrades().add(grade);
        return gradeRepository.save(grade);
    }

    private Teacher createTeacher(Role teacherRole, Collection<Subject> subjects, Collection<SchoolClass> schoolClasses, int random) {
        Subject taughtSubject = findAny(subjects);
        SchoolClass schoolClass = findAny(schoolClasses);
        SchoolClass ledClass = schoolClasses.stream().filter(schoolClass1 -> schoolClass1.getLeadingTeacher() == null).findAny().orElse(null);
        Teacher teacher = new Teacher();
        teacher.setSchoolClasses(new HashSet<>(Collections.singletonList(schoolClass)));
        schoolClass.getTeachers().add(teacher);
        teacher.setIsHeadTeacher(true);
        teacher.setTaughtSubject(taughtSubject);
        taughtSubject.getTeachers().add(teacher);
        teacher.setLedClass(ledClass);
        if (ledClass != null)
            ledClass.setLeadingTeacher(teacher);
        teacher.setUsername("jano3" + random);
        teacher.setPassword(bCryptPasswordEncoder.encode("jano3" + random));
        teacher.setEmail("jano3" + random + "@wp.pl");
        teacher.setName("janoo3" + random);
        teacher.setSurname("janowicz3" + random);
        teacher.setRole(teacherRole);
        teacherRole.getUsers().add(teacher);
        teacher = teacherRepository.save(teacher);
        if (ledClass != null) {
            ledClass.setTeachers(new HashSet<>(Collections.singletonList(teacher)));
            schoolClassRepository.save(ledClass);
        }
        return teacher;
    }

    private Parent createParent(Role parentRole, int random) {
        Parent parent = new Parent();
        parent.setUsername("jano2" + random);
        parent.setPassword(bCryptPasswordEncoder.encode("jano2" + random));
        parent.setEmail("jano2" + random + "@wp.pl");
        parent.setName("janoo2" + random);
        parent.setSurname("janowicz2" + random);
        parent.setRole(parentRole);
        parentRole.getUsers().add(parent);
        return parentRepository.save(parent);
    }

    private Student createStudent(Role studentRole, Collection<Parent> parents, Collection<SchoolClass> schoolClasses, int random) {
        SchoolClass schoolClass = findAny(schoolClasses);
        Parent parent = findAny(parents);
        Student student = new Student();
        student.setSchoolClass(schoolClass);
        schoolClass.getStudents().add(student);
        student.setParent(parent);
        parent.getChildren().add(student);
        student.setHasAccount(true);
        student.setUsername("jano1" + random);
        student.setPassword(bCryptPasswordEncoder.encode("jano1" + random));
        student.setEmail("jano1" + random + "@wp.pl");
        student.setName("janoo1" + random);
        student.setSurname("janowicz1" + random);
        student.setRole(studentRole);
        studentRole.getUsers().add(student);
        return studentRepository.save(student);
    }

    private <T> Collection<T> merge(Collection<T> collection1, Collection<T> collection2) {
        HashSet<T> hashSet = new HashSet<>(collection1);
        hashSet.addAll(collection2);
        return hashSet;
    }

    private <T> T findAny(Collection<T> collection) {
        return collection.stream()
                .skip((int) (collection.size() * Math.random()))
                .findFirst()
                .orElse(null);
    }

    private int getRandom(int bound) {
        return random.nextInt(bound);
    }

}
