package com.javalabs.exception.common;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;

@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<String> errors = new ArrayList<>();
		for (final FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			errors.add("Validation Error found in " + fieldError.getField() + " : " + fieldError.getDefaultMessage());
		}

		for (final ObjectError objectError : ex.getBindingResult().getGlobalErrors()) {
			errors.add(objectError.getObjectName() + " : " + objectError.getDefaultMessage());
		}
		ExceptionResponseEntity exceptionResponseEntity = new ExceptionResponseEntity(new Date(), "Validation Failed",
				errors.toString());
		return new ResponseEntity<Object>(exceptionResponseEntity, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex, WebRequest request)
			throws IOException {
		ExceptionResponseEntity exceptionResponseEntity = new ExceptionResponseEntity(new Date(), "Invalid Request",
				ex.getMessage());
		return new ResponseEntity<Object>(exceptionResponseEntity, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> accessDenidException(AccessDeniedException ex, WebRequest request) {
		ExceptionResponseEntity exceptionResponseEntity = new ExceptionResponseEntity(new Date(),
				"Authorization Failed", ex.getMessage());
		return new ResponseEntity<Object>(exceptionResponseEntity, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Object> authenticationException(AuthenticationException ex, WebRequest request) {
		ExceptionResponseEntity exceptionResponseEntity = new ExceptionResponseEntity(new Date(),
				"Authentication Failed", ex.getMessage());
		return new ResponseEntity<Object>(exceptionResponseEntity, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> entityNotFoundException(EntityNotFoundException ex, WebRequest request) {
		ExceptionResponseEntity exceptionResponseEntity = new ExceptionResponseEntity(new Date(),
				"Entity does not exist", ex.getMessage());
		return new ResponseEntity<Object>(exceptionResponseEntity, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public void GenericException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
}