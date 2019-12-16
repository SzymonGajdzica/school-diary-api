package pl.polsl.school.diary.api.note;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class NotePatch {

    @ApiModelProperty(required = true, example = "Bad behaviour on school trip")
    private String title;

    @ApiModelProperty(required = true, example = "Tom was...", position = 1)
    private String description;

}