package pl.polsl.school.diary.api.note;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
public class NoteView {

    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(required = true, example = "Bad behaviour on school trip", position = 1)
    private String title;

    @ApiModelProperty(required = true, example = "Tom was...", position = 2)
    private String description;

    @ApiModelProperty(required = true, position = 3)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createDate;

    @ApiModelProperty(required = true, position = 4)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updateDate;

    @ApiModelProperty(required = true, position = 5)
    private Long teacherId;

    public NoteView(Note note){
        this(note.getId(),
                note.getTitle(),
                note.getDescription(),
                note.getCreateDate(),
                note.getUpdateDate(),
                note.getTeacher().getId());
    }

}
