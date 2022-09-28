package com.ucp.app.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ucp.model.Response;
import com.ucp.service.DashBoardService;

@RestController
@RequestMapping("api/dashboard")
public class DashBoardController {

	private static final Logger LOGGER = LogManager.getLogger(DashBoardController.class);

	@Autowired
	DashBoardService dashBoardService;

	@RequestMapping(value = "/userstage", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Response getUserstage(@RequestParam(name = "collabId", required = true) int collabId,
			@RequestParam(name = "participantId", required = false, defaultValue = "1") String participantId,
			@RequestHeader("authToken") String authToken) {
		return dashBoardService.getDashboardSuccessProfileDetailsById(collabId, participantId, authToken);
	}
}
