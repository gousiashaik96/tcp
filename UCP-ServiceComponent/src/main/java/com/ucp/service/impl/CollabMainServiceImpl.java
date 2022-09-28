package com.ucp.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ucp.data.model.entity.CollabParticipantStatus;
import com.ucp.data.model.entity.CollabParticipants;
import com.ucp.data.model.entity.SpCollabMain;
import com.ucp.data.repo.dao.AppDAO;
import com.ucp.data.repo.dao.CollabMainDAO;
import com.ucp.model.CollabMain;
import com.ucp.model.CollabParticipant;
import com.ucp.model.QueryModel;
import com.ucp.model.Response;
import com.ucp.service.CollabMainService;
import com.ucp.service.exception.ResourceNotFoundException;
import com.ucp.sp.model.AuthenticatedUser;
import com.ucp.util.ResponseUtil;
import com.ucp.util.successprofile.DashboardStatusUtil;

@Transactional
@Service
public class CollabMainServiceImpl implements CollabMainService {
	private static final Logger LOGGER = LogManager.getLogger(CollabMainServiceImpl.class);

	@Autowired
	CollabMainDAO collabMainDAO;

	@Autowired
	AppDAO appDAO;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	AuthenticatedUser user;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ResponseUtil responseUtil;

	@Override
	public List<CollabMain> getAllCollabMain() throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		List<CollabMain> collabMainList = new ArrayList<>();
		List<SpCollabMain> collabMainSpList = collabMainDAO.findAll();
		collabMainSpList.stream().forEach(collab -> {
			if (null != collab) {
				collabMainList.add(convertCollMainToDto(collab));
			}
		});
		if (collabMainList.isEmpty()) {
			throw new ResourceNotFoundException("Collab details not found");
		}
		return collabMainList;
	}

	@Override
	public Response getCollabMainById(int collabId) throws ResourceNotFoundException {
		Optional<SpCollabMain> collabMainOpt = collabMainDAO.findById(collabId);
		if (collabMainOpt.isPresent()) {
			return responseUtil.getResponse(convertCollMainToDto(collabMainOpt.get()));
		} else {
			throw new ResourceNotFoundException("Collab details not found");
		}
	}

	@Override
	public CollabMain addCollabMain(CollabMain collabMain) {
		// TODO Auto-generated method stub
		List<CollabParticipants> collabParticipants = new ArrayList<>();
		SpCollabMain CollabMainEO = null;
		try {
			SpCollabMain spCollabMain = convertCollabMainDtoToEntity(collabMain);
			if (CollectionUtils.isNotEmpty(collabMain.getCollabParticipants())) {
				collabMain.getCollabParticipants().stream().forEach(participant -> {
					if (null != participant) {
						CollabParticipants collabParticipant = convertCollabParticipantToEntity(participant,
								spCollabMain);
						collabParticipants.add(collabParticipant);
					}
				});
				spCollabMain.setCollabParticipants(collabParticipants);
			}
			CollabMainEO = collabMainDAO.save(spCollabMain);
			collabMain = convertCollMainToDto(CollabMainEO);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return collabMain;
	}

	@Override
	public void removeCollabMain(int collabId) {
		// TODO Auto-generated method stub
		Optional<SpCollabMain> collabMainOpt = collabMainDAO.findById(collabId);
		if (collabMainOpt.isPresent()) {
			collabMainDAO.delete(collabMainOpt.get());
		}
	}

	private CollabMain convertCollMainToDto(SpCollabMain spCollabMain) {
		CollabMain collabMain = modelMapper.map(spCollabMain, CollabMain.class);
		return collabMain;
	}

	private SpCollabMain convertCollabMainDtoToEntity(CollabMain collabMain) {
		SpCollabMain spCollabMain = modelMapper.map(collabMain, SpCollabMain.class);
		return spCollabMain;
	}

	private CollabParticipants convertCollabParticipantToEntity(CollabParticipant collabParticipant,
			SpCollabMain spCollabMain) {
		CollabParticipants collab_participant = modelMapper.map(collabParticipant, CollabParticipants.class);
		collab_participant.setSpCollabMain(spCollabMain);
		collab_participant.setCollabParticipantStatuses(addCompetencySurveyStatus(collab_participant));
		return collab_participant;
	}

	private List<CollabParticipantStatus> addCompetencySurveyStatus(CollabParticipants collabParticipants) {
		List<String> status = Stream.of(DashboardStatusUtil.STATUS_TYPE.values())
				.map(DashboardStatusUtil.STATUS_TYPE::getName).collect(Collectors.toList());
		List<CollabParticipantStatus> participantStatusList = Lists.newArrayList();
		status.stream().forEach(objects -> {
			CollabParticipantStatus participantStatus = new CollabParticipantStatus();
			participantStatus.setStatus(DashboardStatusUtil.START);
			participantStatus.setSortType(objects);
			participantStatus.setCollabParticipant(collabParticipants);
			participantStatus.setCreatedDate(new Date());
			participantStatus.setUpdatedDate(new Date());
			participantStatusList.add(participantStatus);

		});
		return participantStatusList;
	}

	@Override
	public List<Map<String, Object>> getQueryResponse(QueryModel queryModel) {
		// TODO Auto-generated method stub
		return appDAO.getQueryResponse(queryModel.getQuery(), queryModel.getQueryParams());
	}

	@Override
	public Map<String, Object> login(Map<String, Object> request) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		final String uri = "https://api.kornferry.com/v1/actions/login";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(request, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity,
				String.class);
		Type type = new TypeToken<Map<String, Object>>() {
		}.getType();
		Map<String, Object> response = gson.fromJson(responseEntity.getBody(), type);
		return response;
	}
}
