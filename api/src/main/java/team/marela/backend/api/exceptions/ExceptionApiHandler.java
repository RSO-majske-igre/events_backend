package team.marela.backend.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.marela.backend.core.exceptions.BadRequestException;
import team.marela.backend.core.exceptions.DataNotFoundException;
import team.marela.backend.core.exceptions.ForbiddenException;

@RestControllerAdvice
public class ExceptionApiHandler {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {DataNotFoundException.class})
    public ResponseEntity<ExceptionApiModel> handleDataNotFoundException(DataNotFoundException exception) {
        return generateResponseEntity(exception, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<ExceptionApiModel> handleForbiddenException(ForbiddenException exception) {
        return generateResponseEntity(exception, HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BadRequestException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionApiModel> handleBadRequestException(Exception exception) {
        return generateResponseEntity(exception, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ExceptionApiModel> handleException(Exception exception) {
        return generateResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionApiModel> generateResponseEntity(Exception exception, HttpStatus status) {
        exception.printStackTrace();
        return ResponseEntity.status(status).body(
                ExceptionApiModel.builder()
                        .exception(exception.getClass().getSimpleName())
                        .exceptionNo(status.value())
                        .exceptionMessage(exception.getMessage())
                        .body(exception.getCause())
                        .build()
        );
    }
}
