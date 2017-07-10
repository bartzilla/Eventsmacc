package de.enmacc.controllers;

import de.enmacc.domain.Error;
import de.enmacc.services.exceptions.EventNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice(basePackageClasses = EventController.class)
public class EventControllerAdvice extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(EventNotFoundException.class)
    ResponseEntity<Error> handleEventNotFoundException(EventNotFoundException e)
    {
        return new ResponseEntity<>(new Error(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        Throwable mostSpecificCause = e.getMostSpecificCause();
        Error errorMessage;
        if (mostSpecificCause != null) {
            String message = mostSpecificCause.getMessage();
            errorMessage = new Error(HttpStatus.BAD_REQUEST.value(), message);
        } else {
            errorMessage = new Error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
        return new ResponseEntity(errorMessage, headers, status);
    }

    @Override
    protected final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> errors = new ArrayList<>(fieldErrors.size());

        for (FieldError fieldError : fieldErrors)
        {
            errors.add(fieldError.getField() + ", " + fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), errors), headers, status);
    }

}
