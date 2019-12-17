package pl.polsl.school.diary.api.schedule;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
@ToString
@AllArgsConstructor
public class ScheduleView {

    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(required = true, position = 1)
    private Long teacherId;

    @ApiModelProperty(required = true, position = 2)
    private Long classroomId;

    @ApiModelProperty(required = true, position = 3)
    private Long schoolClassId;

    @ApiModelProperty(required = true, example = "2", position = 4)
    private Short day;

    @ApiModelProperty(required = true, position = 5)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime;

    @ApiModelProperty(required = true, position = 6)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime;

    public ScheduleView(Schedule schedule) {
        this(schedule.getId(),
                schedule.getTeacher().getId(),
                schedule.getClassroom().getId(),
                schedule.getSchoolClass().getId(),
                schedule.getDay(),
                schedule.getStartTime(),
                schedule.getEndTime());
    }
}
