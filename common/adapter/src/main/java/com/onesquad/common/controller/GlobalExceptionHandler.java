package com.onesquad.common.controller;

import com.onesquad.common.exception.DomainRuleViolated;
import com.onesquad.common.exception.IllegalOperationException;
import com.onesquad.common.exception.InvalidSearchCriteriaException;
import com.onesquad.common.exception.MalformedDataException;
import com.onesquad.common.exception.NotFoundException;
import com.onesquad.common.exception.OverlappingReservationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({
            InvalidSearchCriteriaException.class,
            IllegalArgumentException.class,
            MalformedDataException.class,
            DomainRuleViolated.class,
            OverlappingReservationException.class
    })
    public ResponseEntity<String> handleBadRequestExceptions(Exception ex) {
        log.error(ex.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(Exception ex) {
        log.error(ex.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<String> handleIllegalOperationException(Exception ex) {
        log.error(ex.toString());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}
