package com.zzepish.accounts.controller.advice;

import com.zzepish.accounts.dto.ErrorResponseDTO;
import com.zzepish.accounts.dto.ResponseDTO;
import com.zzepish.accounts.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    private final WebRequest webRequest;

    public ControllerAdvice(WebRequest webRequest) {
        this.webRequest = webRequest;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }
}
