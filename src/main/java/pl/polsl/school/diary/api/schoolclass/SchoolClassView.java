package pl.polsl.school.diary.api.schoolclass;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class SchoolClassView {

    @ApiModelProperty(required = true, example = "0")
    private Long id;

    @ApiModelProperty(required = true, example = "18", position = 1)
    private String symbol;

    public SchoolClassView(SchoolClass schoolClass){
        id = schoolClass.getId();
        symbol = schoolClass.getSymbol();
    }

}
