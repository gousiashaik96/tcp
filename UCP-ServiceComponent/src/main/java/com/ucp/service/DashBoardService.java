package com.ucp.service;

import com.ucp.model.Response;

public interface DashBoardService {
	public Response getDashboardSuccessProfileDetailsById(int collabId,String participantId,String authToken);
}
