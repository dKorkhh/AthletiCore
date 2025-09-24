package com.example.athleticore.exception;

import com.example.athleticore.exception.data.CallApiException;
import com.example.athleticore.exception.user.BadCredentialsException;
import com.example.athleticore.exception.user.DeletingTrainerException;
import com.example.athleticore.exception.data.NoDataFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionalHandler {
    @ExceptionHandler(DeletingTrainerException.class)
    public ResponseEntity<String> handleForbiddenDeletingTrainer(DeletingTrainerException ex) {
        log.atInfo().log("Handled DeletingTrainerException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
        log.atInfo().log("Handled DeletingTrainerException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<String> handleBadCredentials(NoDataFoundException ex) {
        log.atInfo().log("Handled DeletingTrainerException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CallApiException.class)
    public ResponseEntity<String> handleCallApiException(CallApiException ex) {
        log.atInfo().log("Handled DeletingTrainerException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation error");
        body.put("cause", exception.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
