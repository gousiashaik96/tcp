package com.ucp.service;

import java.util.List;

import com.ucp.nosql.model.Competency;
import com.ucp.nosql.model.CompanyCulture;
import com.ucp.nosql.model.RoleRequirementSlider;
import com.ucp.service.exception.ResourceNotFoundException;

public interface CompetencyService {
	Competency saveCompetency(Competency collabParticipant) throws ResourceNotFoundException;
	Competency getCompetencyById(String id);
	List<Competency> findAllCompetencies(int page, int limit);
	void removeCompetencyById(String id);
	CompanyCulture saveCompanyCulture(CompanyCulture companyCulture);
	RoleRequirementSlider saveRoleRequirementSlider(RoleRequirementSlider roleRequirementSlider);
}
