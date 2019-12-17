package pl.polsl.school.diary.api.schedule;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/schedules")
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleRepository scheduleRepository;

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

}
