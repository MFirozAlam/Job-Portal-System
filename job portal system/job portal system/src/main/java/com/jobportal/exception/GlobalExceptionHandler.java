package com.jobportal.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<Map<String,Object>> bad(IllegalArgumentException e){return body(HttpStatus.BAD_REQUEST,e.getMessage());}
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<Map<String,Object>> denied(IllegalStateException e){return body(HttpStatus.FORBIDDEN,e.getMessage());}
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException e){
        Map<String,String> errors=new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors().forEach(x->errors.put(x.getField(),x.getDefaultMessage()));
        Map<String,Object> m=new LinkedHashMap<>();m.put("message","Validation failed");m.put("errors",errors);return ResponseEntity.badRequest().body(m);
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<Map<String,Object>> other(Exception e){return body(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());}
    private ResponseEntity<Map<String,Object>> body(HttpStatus s,String msg){return ResponseEntity.status(s).body(Map.of("message",msg==null?"Request failed":msg));}
}
