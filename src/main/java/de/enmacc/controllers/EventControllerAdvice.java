package de.enmacc.controllers;

import de.enmacc.domain.ClientErrorInformation;
import de.enmacc.services.exceptions.EventNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackageClasses = EventController.class)
public class EventControllerAdvice extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(Throwable.class)
    ResponseEntity<ClientErrorInformation> handleControllerException(HttpServletRequest request, Throwable ex)
    {
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(new ClientErrorInformation(status.value(), ex.getMessage()), status);
    }

    @ExceptionHandler(EventNotFoundException.class)
    ResponseEntity<ClientErrorInformation> handleEventNotFoundException(EventNotFoundException ex) {
        return new ResponseEntity<>(new ClientErrorInformation(HttpStatus.NOT_FOUND.value(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    private HttpStatus getStatus(HttpServletRequest request)
    {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
