package com.javalabs.exception.common;

import org.springframework.http.HttpStatus;

final class InternalSystemErrorException extends MyCustomException {
	private static final long serialVersionUID = 1L;
	private static final String CODE = "500.001";
    private static final String STATUS = "500.001";
    private static final String REASON = "Internal System Error. An Unexpected Error Occurred while executing Module specific Service Logic during accessing and/or context transformation of ${attribute} , Error Message: ${message}";
    private static final String ERROR_MESSAGE = "Internal Processing Failure - System. Please contact System Admin.";

    public InternalSystemErrorException(String attribute, String detail) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, Error.builder().code(CODE).status(STATUS).reason(REASON.replace("${attribute}", attribute).replace("${message}", detail)).message(ERROR_MESSAGE).build());
    }
}