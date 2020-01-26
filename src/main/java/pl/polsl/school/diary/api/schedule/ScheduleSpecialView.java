package pl.polsl.school.diary.api.schedule;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class ScheduleSpecialView {

    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(required = true, position = 1)
    private Long teacherId;

    @ApiModelProperty(required = true, position = 2)
    private String teacherName;

    @ApiModelProperty(required = true, position = 3)
    private String teacherSurname;

    @ApiModelProperty(required = true, position = 4)
    private String subjectName;

    @ApiModelProperty(required = true, position = 5)
    private String classroomSymbol;

    @ApiModelProperty(required = true, position = 6)
    private String schoolClassSymbol;

    @ApiModelProperty(required = true, position = 7)
    private Short day;

    @ApiModelProperty(required = true, position = 8)
    private Short lessonNumber;

    public ScheduleSpecialView(Schedule schedule) {
        this(schedule.getId(),
                schedule.getTeacher().getId(),
                schedule.getTeacher().getName(),
                schedule.getTeacher().getSurname(),
                schedule.getTeacher().getTaughtSubject().getName(),
                schedule.getClassroom().getSymbol(),
                schedule.getSchoolClass().getSymbol(),
                schedule.getDay(),
                parseLessonNumber(schedule));
    }

    private static Short parseLessonNumber(Schedule schedule){
        switch (schedule.getStartTime().getHour()){
            case 8: return 0;
            case 9: return 1;
            case 10: return 2;
            case 11: return 3;
            case 12: return 4;
            case 13: return 5;
            case 14: return 6;
            default: return 7;
        }
    }

}
