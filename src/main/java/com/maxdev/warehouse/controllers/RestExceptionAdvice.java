package com.maxdev.warehouse.controllers;

import com.maxdev.warehouse.exceptions.AccessDeniedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;

@ControllerAdvice
public class RestExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<String> accessDeniedHandler(AccessDeniedException ex){
        if (ex.showLoginPage()) {
            HttpHeaders header = new HttpHeaders();
            header.setLocation(URI.create("/login"));
            return new ResponseEntity<String>(header,HttpStatus.LOCKED);
        }

//        HttpStatus status = ex.showLoginPage()?HttpStatus.LOCKED:HttpStatus.FORBIDDEN;

        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

}
