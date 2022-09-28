package com.ucp.service;

import java.util.List;
import java.util.Map;

import com.ucp.model.CollabMain;
import com.ucp.model.QueryModel;
import com.ucp.model.Response;
import com.ucp.service.exception.ResourceNotFoundException;

public interface CollabMainService {
	List<CollabMain> getAllCollabMain() throws ResourceNotFoundException;

	Response getCollabMainById(int collabId) throws ResourceNotFoundException;

	CollabMain addCollabMain(CollabMain collabMain);

	void removeCollabMain(int collabId);
	
	List<Map<String, Object>> getQueryResponse(QueryModel queryModel);
	
	Map<String,Object> login(Map<String,Object> request);
}
