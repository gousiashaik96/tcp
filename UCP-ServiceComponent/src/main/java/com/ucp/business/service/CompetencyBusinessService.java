package com.ucp.business.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ucp.data.model.entity.CollabParticipantStatus;
import com.ucp.data.model.entity.CollabParticipants;
import com.ucp.data.repo.dao.CollabParticipantStatusDAO;
import com.ucp.service.exception.ResourceNotFoundException;
import com.ucp.util.successprofile.DashboardStatusUtil;

@Component
public class CompetencyBusinessService {
	@Autowired
	CollabParticipantStatusDAO collabParticipantStatusDAO;

	public void updateParticipantStatus(CollabParticipants collabParticipant) throws ResourceNotFoundException {
		try {
			List<CollabParticipantStatus> collabParticipantStatusList = collabParticipant.getCollabParticipantStatuses()
					.stream()
					.filter(partStatus -> null != partStatus && StringUtils.isNotEmpty(partStatus.getSortType())
							&& partStatus.getSortType().equalsIgnoreCase(DashboardStatusUtil.COMPENTENCIES))
					.collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(collabParticipantStatusList)) {
				CollabParticipantStatus collabParticipantStatus = collabParticipantStatusList.get(0);
				collabParticipantStatus.setUpdatedDate(new Date());
				collabParticipantStatus.setStatus(DashboardStatusUtil.RESUME);
				collabParticipantStatusDAO.save(collabParticipantStatus);
			}
		} catch (Exception ex) {
			throw new ResourceNotFoundException("Collab details not found");
		}
	}
}
