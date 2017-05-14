package com.packtpub.yummy;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorAdvice {

    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody
    String elementNotFound(IncorrectResultSizeDataAccessException e){
        return "Could not find element! "+e.getMessage();
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody
    String elementNotFound(OptimisticLockingFailureException e) {
        return "Conflict! " + e.getMessage();
    }
}
