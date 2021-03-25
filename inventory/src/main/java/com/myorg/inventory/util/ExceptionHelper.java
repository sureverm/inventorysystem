package com.myorg.inventory.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.InputMismatchException;

@ControllerAdvice
public class ExceptionHelper {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    @ExceptionHandler(value = { InvalidParameterException.class })
    public ResponseEntity<Object> handleInvalidInputException(InvalidParameterException ex) {
        logger.error("Invalid Input Exception: ",ex.getMessage());
        return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { InputMismatchException.class })
    public void handleInputMismatchException(InputMismatchException ex, HttpServletResponse response) throws IOException {
        logger.error("InputMismatchException Exception: ",ex.getMessage());
        response.sendError(HttpStatus.PRECONDITION_FAILED.value(), ex.getMessage());
    }

    @ExceptionHandler(value = { HttpClientErrorException.Unauthorized.class })
    public ResponseEntity<Object> handleUnauthorizedException(HttpClientErrorException.Unauthorized ex) {
        logger.error("Unauthorized Exception: ",ex.getMessage());
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /*@ExceptionHandler(value = { Exception.class })
    public void springHandleNotFound(Exception ex, HttpServletResponse response) throws IOException {
        logger.error("Exception Exception: ",ex.getMessage());
        response.sendError(response.getStatus(), ex.getMessage());
    }*/

    @ExceptionHandler(value = { IllegalStateException.class })
    public void handleIllegalStateException(IllegalStateException ex, HttpServletResponse response) throws IOException {
        logger.error("IllegalStateException Exception: ",ex.getMessage());
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ExceptionHandler(value = { NullPointerException.class })
    public void handleNullPointerException(NullPointerException ex, HttpServletResponse response) throws IOException {
        logger.error("NullPointerException Exception: ",ex.getMessage());
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Empty or Null value sent for "+ex.getMessage());
    }
}