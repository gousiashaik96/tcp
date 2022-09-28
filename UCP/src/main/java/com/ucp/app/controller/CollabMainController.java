package com.ucp.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ucp.data.model.entity.SpCollabMain;
import com.ucp.model.CollabMain;
import com.ucp.model.QueryModel;
import com.ucp.model.Response;
import com.ucp.service.CollabMainService;
import com.ucp.service.exception.ResourceNotFoundException;

@RestController
@RequestMapping("api")
public class CollabMainController {

	@Autowired
	CollabMainService collabMainService;

	@RequestMapping(value = "/getAllCollabMain", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<CollabMain> getAllCollabMain() throws ResourceNotFoundException {
		return collabMainService.getAllCollabMain();
	}

	@RequestMapping(value = "/getCollabMainById", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Response getCollabMainById(@RequestParam(name = "collabId", required = true) int collabId) throws ResourceNotFoundException {
		return collabMainService.getCollabMainById(collabId);
	}

	@RequestMapping(value = "/addCollabMain", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public CollabMain addCollabMain(@RequestBody CollabMain collabMain) {
		return collabMainService.addCollabMain(collabMain);
	}

	@RequestMapping(value = "/removeCollabMain", method = RequestMethod.DELETE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String removeCollabMain(@RequestParam(name = "collabId", required = true) int collabId) {
		collabMainService.removeCollabMain(collabId);
		return "SUCCESS";
	}

	@RequestMapping(value = "/getQueryResult", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<Map<String, Object>> getQueryResult(@RequestBody QueryModel queryModel) {
		return collabMainService.getQueryResponse(queryModel);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> login(@RequestBody Map<String, Object> request) {
		return collabMainService.login(request);
	}

}
