package pl.polsl.school.diary.api.authentication;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
public class AuthenticationView {

    @ApiModelProperty(required = true, example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYW5vIiwiZXhwIjoxNTc0OTQ1NDY3LCJpYXQiOjE1NzQ5Mjc0Njd9.1nVS9zoTiJ7ZRBLRsKwxf2rrcTxn6M6HfCRHNvnI5nC-52cvjtR0PiLMjU4XQaUkKPywttOi8OS6jeloHbQ8LA")
    @Getter(AccessLevel.PUBLIC)
    private final String token;

    @ApiModelProperty(required = true, position = 1)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Getter(AccessLevel.PUBLIC)
    private final Date expirationDate;



}