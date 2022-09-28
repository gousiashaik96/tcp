package com.ucp.data.repo.dao;

import com.ucp.data.model.entity.CollabParticipants;
import com.ucp.data.model.entity.SpCollabMain;

public interface SuccessProfileDAO {
	public SpCollabMain getCollabMainBySpId(String spId);
	public CollabParticipants getCollabMainByCollabIdAndParticipantId(Integer collabId,String participantId);
}
