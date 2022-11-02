package team.marela.backend.api.exceptions;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionApiModel {
    private String exception;
    private String exceptionMessage;
    private Integer exceptionNo;
    private Object body;
}
