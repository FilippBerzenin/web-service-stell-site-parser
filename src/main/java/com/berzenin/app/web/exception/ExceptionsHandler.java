package com.berzenin.app.web.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionsHandler {
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Items Not Found")
	@ExceptionHandler(NotFoundException.class)
	protected void handleThereIsItemNotFoundException(HttpServletRequest req, NotFoundException e) {
		log.error("Item Not Found");
	}
}
