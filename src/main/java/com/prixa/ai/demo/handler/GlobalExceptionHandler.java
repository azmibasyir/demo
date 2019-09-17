package com.prixa.ai.demo.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        body.put("status", status.value());
        //Get all errors
        List<FieldError> errors = ex.getBindingResult()
                .getFieldErrors();
        body.put("errors", errorsMapper(errors));
        logger.info(request.getDescription(false) + " " + errorsMapper(errors).toString());
        return new ResponseEntity<>(body, headers, status);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        body.put("status", status.value());
        body.put("errors", ex.getCause().getMessage());
        logger.info(ex.getCause().toString());
        return new ResponseEntity<>(body, headers, status);
    }

    private TreeMap<String, List<String>> errorsMapper(List<FieldError> errors) {
        HashMap<String, List<String>> errorsList = new HashMap<>();

        errors.forEach(x -> {
            if (errorsList.containsKey(x.getField())) {
                errorsList.get(x.getField()).add(x.getDefaultMessage());
            } else {
                errorsList.put(x.getField(), new ArrayList<>(Collections.singletonList(x.getDefaultMessage())));
            }
        });
        return new TreeMap<>(errorsList);
    }
}
