package pl.polsl.school.diary.api.schedule;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.school.diary.api.exception.NotAuthorizedActionException;
import pl.polsl.school.diary.api.parent.Parent;
import pl.polsl.school.diary.api.student.Student;
import pl.polsl.school.diary.api.teacher.Teacher;
import pl.polsl.school.diary.api.token.TokenRepository;
import pl.polsl.school.diary.api.user.User;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/schedules")
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleRepository scheduleRepository;
    private final TokenRepository tokenRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ScheduleView> getSchedules(@RequestParam(required = false) Long teacherId,
                                          @RequestParam(required = false) Long classroomId,
                                          @RequestParam(required = false) Long schoolClassId) {
        Set<Schedule> schedules;
        if(teacherId == null && classroomId == null && schoolClassId == null)
            schedules = scheduleRepository.findAllBy();
        else if(classroomId == null && schoolClassId == null)
            schedules = scheduleRepository.findAllByTeacherId(teacherId);
        else if(teacherId == null && schoolClassId == null)
            schedules = scheduleRepository.findAllByClassroomId(classroomId);
        else if(teacherId == null && classroomId == null)
            schedules = scheduleRepository.findAllBySchoolClassId(schoolClassId);
        else if(teacherId == null)
            schedules = scheduleRepository.findAllBySchoolClassIdAndClassroomId(schoolClassId, classroomId);
        else if(classroomId == null)
            schedules = scheduleRepository.findAllBySchoolClassIdAndTeacherId(schoolClassId, teacherId);
        else if(schoolClassId == null)
            schedules = scheduleRepository.findAllByClassroomIdAndTeacherId(classroomId, teacherId);
        else
            schedules = scheduleRepository.findAllBySchoolClassIdAndClassroomIdAndTeacherId(schoolClassId, classroomId, teacherId);
        return schedules.stream().map(ScheduleView::new).collect(Collectors.toSet());
    }

    @GetMapping(value = "/special",produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ScheduleSpecialView> getSpecialSchedules(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader) {
        Set<Schedule> schedules;
        User user = tokenRepository.getUserFromHeader(tokenHeader);
        if(user instanceof Teacher)
            schedules = scheduleRepository.findAllByTeacherId(user.getId());
        else if(user instanceof Parent){
            schedules = new HashSet<>();
            ((Parent) user).getChildren().forEach(student -> schedules.addAll(scheduleRepository.findAllBySchoolClassId(student.getSchoolClass().getId())));
        } else if (user instanceof Student)
            schedules = scheduleRepository.findAllBySchoolClassId(((Student) user).getSchoolClass().getId());
        else
            throw new NotAuthorizedActionException("Unknown user type");
        return schedules.stream().map(ScheduleSpecialView::new).collect(Collectors.toSet());
    }

}
