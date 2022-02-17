package com.trianasalesianos.dam.Miarma.errors;

import com.trianasalesianos.dam.Miarma.errors.models.ApiError;
import com.trianasalesianos.dam.Miarma.errors.models.ApiSubError;
import com.trianasalesianos.dam.Miarma.errors.models.ApiValidationSubError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    public final ResponseEntity<Object> buildApiError(Exception ex, WebRequest webRequest){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ((ServletWebRequest)webRequest).getRequest().getRequestURI()));
    }


    public ResponseEntity<Object> buildApiErrorWithSubError(HttpStatus status, String message, WebRequest webRequest, List<ApiSubError> subErrors){
        return ResponseEntity
                .status(status)
                .body(new ApiError(status, message, ((ServletWebRequest)webRequest).getRequest().getRequestURI(),subErrors));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildApiError(ex,request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ApiSubError> subErrorList = new ArrayList<>();

        ex.getAllErrors().forEach(error -> {

            if(error instanceof FieldError){
                FieldError fieldError = (FieldError) error;
                subErrorList.add(
                        ApiValidationSubError.builder()
                                .object(fieldError.getObjectName())
                                .field(fieldError.getField())
                                .objectError(fieldError.getRejectedValue())
                                .message(fieldError.getDefaultMessage())
                                .build());
            }else {
                ObjectError objectError = (ObjectError) error;

                subErrorList.add(
                        ApiValidationSubError.builder()
                                .object(objectError.getObjectName())
                                .message(objectError.getDefaultMessage())
                                .build()
                );
            }
        });

        return buildApiErrorWithSubError(HttpStatus.BAD_REQUEST, "//properties mensaje",
                request,subErrorList.isEmpty() ? null : subErrorList);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(EntityNotFoundException entityNotFoundException, WebRequest webRequest){
        return buildApiError(entityNotFoundException,webRequest);
    }


}