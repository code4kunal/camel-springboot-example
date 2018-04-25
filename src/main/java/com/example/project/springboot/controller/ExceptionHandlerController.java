package com.example.project.springboot.controller;


import com.example.project.springboot.model.ApiResponse;
import com.example.project.springboot.model.ApiResponseError;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.security.InvalidParameterException;


@ControllerAdvice
public class ExceptionHandlerController {

  private static final Logger LOGGER = LogManager.getLogger(ExceptionHandlerController.class);

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
      @Override
      public void setAsText(String text) throws IllegalArgumentException {
        setValue(text.toLowerCase());
      }
    });
  }


  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiResponse> notFound(NotFoundException e) {
    LOGGER.warn("Not Found: ", e);
    return new ResponseEntity<>(new ApiResponseError(e.getMessage(), "404"), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiResponse> requestNotSupported(HttpRequestMethodNotSupportedException e) {
    LOGGER.warn("HttpRequestMethodNotSupported: ", e);
    return new ResponseEntity<>(new ApiResponseError("Request method not supported", "INVALID_REQUEST"),
        HttpStatus.METHOD_NOT_ALLOWED);
  }


  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ApiResponse> contentTypeNotSuppportedException(HttpMediaTypeNotSupportedException e) {
    LOGGER.warn("Media Type not supported: ", e);
    return new ResponseEntity<>(new ApiResponseError(String.format("Content-Type %s not supported. ", e.getContentType()), "UNSUPPORTED_MEDIA_TYPE"), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ApiResponse> resourceNotFoundError(NoHandlerFoundException e, HttpServletRequest req) {
    LOGGER.warn("Resource not found: ", e);
    return new ResponseEntity<>(new ApiResponseError("Invalid API, Please check URL. Requested URL: " + req.getRequestURI(), "RESOURCE_NOT_FOUND"),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IOException.class)
  public ResponseEntity<ApiResponse> ioExceptions(IOException e, HttpServletRequest req) {
    LOGGER.error("java.io.Exception via exception handler: ", e);
    return new ResponseEntity<>(new ApiResponseError("Something went wrong, please try again later", "INTERNAL_SERVER_ERROR"),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ServletRequestBindingException.class)
  public ResponseEntity<ApiResponse> invalidAuth(ServletRequestBindingException e) {
    LOGGER.error("INTERNAL_SERVER_ERROR: ", e);
    return new ResponseEntity<>(new ApiResponseError("Invalid parameters for this API", "INVALID_PARAMETERS"),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ApiResponse> invalidAuth(MissingServletRequestParameterException e) {
    LOGGER.warn("Missing Request Parameter: ", e);
    return new ResponseEntity<>(new ApiResponseError("Must provide " + e.getParameterName() + " for this Api.", "INSUFFICIENT_PARAMETERS"),
        HttpStatus.NOT_ACCEPTABLE);
  }




}
