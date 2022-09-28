package com.ucp.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ucp.model.CollabMain;
import com.ucp.model.Email;
import com.ucp.model.Response;
import com.ucp.service.EmailService;

@RestController
@RequestMapping("api")
public class EmailController {
	@Autowired
	EmailService emailService;

	@RequestMapping(value = "/sendemail", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Response getSuccessPropfileDetailsById(@RequestParam(name = "id", required = true) int spId,
			@RequestParam(name = "participantId", required = true) String participantId,
			@RequestHeader("authToken") String authToken,@RequestBody Email email) {
		return emailService.sendEmailNotification(spId, participantId, authToken, email);
	}
}
