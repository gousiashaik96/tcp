package com.ucp.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ucp.model.Response;
import com.ucp.model.SPConfigOptionMaster;
import com.ucp.service.SuccessProfileConfigService;

@RestController
@RequestMapping("api")
public class SuccessProfileController {
	@Autowired
	SuccessProfileConfigService successProfileConfigService;

	@RequestMapping(value = "/getSuccessProfileById", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Response getSuccessPropfileDetailsById(@RequestParam(name = "collabId", required = true) int collabId,
			@RequestParam(name = "type", required = true) String type,
			@RequestParam(name = "participantId", required = true) String participantId,
			@RequestHeader("authToken") String authToken) {
		return successProfileConfigService.getSuccessProfileDetailsById(collabId, participantId, type, authToken);
	}

	@RequestMapping(value = "/getAllSPConfig", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<SPConfigOptionMaster> getAllSPConfig() {
		return successProfileConfigService.getAllSPConfigs();
	}

	@RequestMapping(value = "/getSPConfigMasterById", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public SPConfigOptionMaster getSPConfigMasterById(
			@RequestParam(name = "spConfigMasterId", required = true) int spConfigMasterId) {
		return successProfileConfigService.getSPConfigById(spConfigMasterId);
	}

	@RequestMapping(value = "/addSPConfigOptionMaster", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public void addSPConfigOptionMaster(@RequestBody SPConfigOptionMaster spConfigOptionMaster) {
		successProfileConfigService.addSuccessProfileOption(spConfigOptionMaster);
	}

	@RequestMapping(value = "/removeSPConfigOptionMaster", method = RequestMethod.DELETE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public void removeSPConfigOptionMaster(
			@RequestParam(name = "spConfigMasterId", required = true) int spConfigMasterId) {
		successProfileConfigService.removeSPConfigMasterById(spConfigMasterId);
	}

}
