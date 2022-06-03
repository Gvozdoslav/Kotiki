package ru.itmo.kotiki.foreign.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itmo.kotiki.data.tool.OwnerNotFoundException;

@ControllerAdvice
public class OwnerNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(OwnerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String ownerNotFoundHandler(OwnerNotFoundException ex) {
        return ex.getMessage();
    }
}