package io.ruslan.top100crypto.controller.advice;

import io.ruslan.top100crypto.exceptions.UserAlreadyExistsException;
import io.ruslan.top100crypto.model.dto.response.AuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<AuthenticationResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        AuthenticationResponse response = new AuthenticationResponse(null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
