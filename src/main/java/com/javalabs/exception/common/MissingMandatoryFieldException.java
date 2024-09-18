package com.javalabs.exception.common;

public class MissingMandatoryFieldException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MissingMandatoryFieldException(String message) {
		super(message);
	}
}
