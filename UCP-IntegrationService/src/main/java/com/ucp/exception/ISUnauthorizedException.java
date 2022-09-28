package com.ucp.exception;

public class ISUnauthorizedException extends Exception{

	private static final long serialVersionUID = 1L;

	public ISUnauthorizedException() {
		super();
	}
	
	public ISUnauthorizedException(String message) {
		super(message);
	}
}
