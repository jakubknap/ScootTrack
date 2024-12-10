package pl.scoottrack.handler;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<ExceptionResponse> handleException(AccountStatusException exp) {
        return ResponseEntity.status(UNAUTHORIZED)
                             .body(ExceptionResponse.builder()
                                                    .error(exp.getMessage())
                                                    .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult()
           .getAllErrors()
           .forEach(error -> {
               var errorMessage = error.getDefaultMessage();
               errors.add(errorMessage);
           });
        return ResponseEntity.status(BAD_REQUEST)
                             .body(ExceptionResponse.builder()
                                                    .validationErrors(errors)
                                                    .build());
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ExceptionResponse> handleException(DomainException exp) {
        log.error(exp);
        return ResponseEntity.status(BAD_REQUEST)
                             .body(ExceptionResponse.builder()
                                                    .error(exp.getMessage())
                                                    .build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(EntityNotFoundException exp) {
        log.error(exp);
        return ResponseEntity.status(NOT_FOUND)
                             .body(ExceptionResponse.builder()
                                                    .error(exp.getMessage())
                                                    .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        log.error(exp);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                             .body(ExceptionResponse.builder()
                                                    .error("Błąd wewnętrzny serwera")
                                                    .build());
    }
}