package com.ucp.service.exception;

import lombok.Data;

@Data
public class ExceptionResponse {

	private String errorMessage;
	private String requestedURI;
}
