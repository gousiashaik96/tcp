package com.ucp.sql.data.repo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ucp.data.model.entity.CollabParticipants;
import com.ucp.data.model.entity.SpCollabMain;
import com.ucp.data.repo.dao.SuccessProfileDAO;

@Repository
public class SuccessProfileDAOImpl implements SuccessProfileDAO {
	
	private Logger LOGGER = LogManager.getLogger(getClass());
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public SpCollabMain getCollabMainBySpId(String spId) {
		// TODO Auto-generated method stub
		List<SpCollabMain> collabMainList = entityManager
				.createQuery("Select c from SpCollabMain c where c.spId=:spId", SpCollabMain.class)
				.setParameter("spId", spId).getResultList();
		if (CollectionUtils.isNotEmpty(collabMainList) && collabMainList.size() > 0) {
			return collabMainList.get(0);
		}
		return null;
	}
	
	public CollabParticipants getCollabMainByCollabIdAndParticipantId(Integer collabId, String participantId) {
		List<CollabParticipants> result = entityManager
				.createQuery(
						"Select cp FROM SpCollabMain sp "
								+ "JOIN CollabParticipants cp ON sp.collabId=cp.spCollabMain.collabId"
								+ "	WHERE sp.collabId=:collabId" + " AND cp.participantId=:participantId",
						CollabParticipants.class)
				.setParameter("collabId", collabId).setParameter("participantId", participantId).getResultList();
		if (result != null && CollectionUtils.isNotEmpty(result)) {
			LOGGER.info("Data not empty");
			return result.get(0);
		}
		return null;
	}

}
