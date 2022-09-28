package com.ucp.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ucp.api.config.API;
import com.ucp.data.model.entity.SpCollabMain;
import com.ucp.data.repo.dao.CollabMainDAO;
import com.ucp.data.repo.dao.SuccessProfileDAO;
import com.ucp.nosql.data.repo.dao.CompetencyNoSqlDAO;
import com.ucp.nosql.model.Competency;
import com.ucp.nosql.model.Critical;
import com.ucp.service.base.ServiceClientHandler;
import com.ucp.service.exception.ResourceNotFoundException;
import com.ucp.sp.model.AuthenticatedUser;
import com.ucp.sp.model.Description;
import com.ucp.sp.model.Profile;
import com.ucp.sp.model.SubCategory;
import com.ucp.util.successprofile.SuccessProfileUtil;
import com.ucp.util.successprofile.transform.SuccessProfileTransformation;

@Component
public class SuccessProfileBusinessService {
	@Autowired
	SuccessProfileUtil successProfileUtil;

	@Autowired
	SuccessProfileDAO successProfileDAO;

	@Autowired
	CompetencyNoSqlDAO competencyNoSQLDAO;
	
	@Autowired
	CollabMainDAO collabMainDAO;

	@Autowired
	SuccessProfileTransformation successProfileTransform;

	@Autowired
	@Qualifier("GetSuccessProfileDetailsByIdHandler")
	ServiceClientHandler SuccessProfileDetailsByIdHandler;
	
	public SpCollabMain getCollabMainById(int collabId) throws ResourceNotFoundException {
		Optional<SpCollabMain> collabMainOpt = collabMainDAO.findById(collabId);
		if (collabMainOpt.isPresent()) {
			return collabMainOpt.get();
		} else {
			throw new ResourceNotFoundException("Collab details not found");
		}
	}

	public Profile getSuccessProfile(int spId, String authToken) {
		AuthenticatedUser authUser = new AuthenticatedUser();
		authUser.setAuthToken(authToken);
		API.PM_SUCCESSPROFILE_DETAIL.setSpId(spId);
		Profile profile = SuccessProfileDetailsByIdHandler.getAPI(API.PM_SUCCESSPROFILE_DETAIL, authUser);
		return profile;
	}

	public List<SubCategory> getSuccesProfileCompetencyCards(int spId, String authToken) {
		List<SubCategory> subCategories = new ArrayList<>();
		try {
			Profile profile = getSuccessProfile(spId, authToken);
			if (null != profile) {
				subCategories = successProfileUtil.getBehaviorCompetencyCardFromSP(profile);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return subCategories;
	}

	public SpCollabMain getCollabMainBySpId(String spId) {
		SpCollabMain spCollabMain = successProfileDAO.getCollabMainBySpId(spId);
		return spCollabMain;
	}

	public Competency getCompetencyByCollabId(SpCollabMain spCollabMain, String participantId) {
		Competency competency = competencyNoSQLDAO.getCompetencyByCollabParticipantId(Integer.toString(spCollabMain.getCollabId()),
				participantId);
		return competency;
	}

	public List<Critical> getCompetencyCardsFromSPDetails(List<SubCategory> subCategories) {
		List<Critical> criticalList = new ArrayList<>();
		List<Description> spDescriptions = successProfileUtil.getCompetencyCardsFromSPDetail(subCategories);
		if (CollectionUtils.isNotEmpty(spDescriptions)) {
			criticalList = successProfileTransform.transformSPDescriptionToCompetenceDesc(spDescriptions);
		}
		return criticalList;
	}
}
