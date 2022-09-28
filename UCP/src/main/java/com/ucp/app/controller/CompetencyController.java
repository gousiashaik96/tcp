package com.ucp.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ucp.nosql.model.Competency;
import com.ucp.nosql.model.CompanyCulture;
import com.ucp.nosql.model.RoleRequirementSlider;
import com.ucp.service.CompetencyService;
import com.ucp.service.exception.ResourceNotFoundException;

@RestController
@RequestMapping("api")
public class CompetencyController {
	@Autowired
	CompetencyService competencyService;

	@RequestMapping(value = "/saveCompetency", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Competency saveCompetency(@RequestBody Competency competency) throws ResourceNotFoundException {
		return competencyService.saveCompetency(competency);
	}

	@RequestMapping(value = "/getCompetencyById", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Competency getCompetencyById(@RequestParam(name = "id", required = true) String id) {
		return competencyService.getCompetencyById(id);
	}

	@RequestMapping(value = "/findAllCompetencies", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<Competency> findAllCompetencies(@RequestParam(name = "page", required = true) int page,
			@RequestParam(name = "limit", required = true) int limit) {
		return competencyService.findAllCompetencies(page, limit);
	}

	@RequestMapping(value = "/removeCollabParticipantById", method = RequestMethod.DELETE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public void removeCompetencyById(@RequestParam(name = "id", required = true) String id) {
		competencyService.removeCompetencyById(id);
	}
	
	@RequestMapping(value = "/saveCompanyCulture", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public CompanyCulture saveCompanyCulture(@RequestBody CompanyCulture companyCulture) {
		return competencyService.saveCompanyCulture(companyCulture);
	}
	
	@RequestMapping(value = "/saveRoleRequirementSlider", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public RoleRequirementSlider saveRoleRequirementSlider(@RequestBody RoleRequirementSlider roleRequirementSlider) {
		return competencyService.saveRoleRequirementSlider(roleRequirementSlider);
	}

}
