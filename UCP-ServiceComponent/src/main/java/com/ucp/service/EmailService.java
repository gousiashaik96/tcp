package com.ucp.service;

import com.ucp.model.Email;
import com.ucp.model.Response;

public interface EmailService {
	public Response sendEmailNotification(int spId,String participantId,String authToken, Email email);

}
