package com.pruebas.exception;

public class MalformedURLException extends RuntimeException {
	
	private static final String DESCRIPTION = "Malformed Url Exception";
	
	public MalformedURLException(String detail) {
		super(DESCRIPTION + ". " + detail);
	}

}
