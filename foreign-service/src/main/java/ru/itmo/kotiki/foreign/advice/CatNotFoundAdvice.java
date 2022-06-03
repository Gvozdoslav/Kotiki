package ru.itmo.kotiki.foreign.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itmo.kotiki.data.tool.CatNotFoundException;

@ControllerAdvice
public class CatNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(CatNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static String catNotFoundHandler(CatNotFoundException ex) {
        return ex.getMessage();
    }
}
