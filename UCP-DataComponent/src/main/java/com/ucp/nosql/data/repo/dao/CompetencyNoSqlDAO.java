package com.ucp.nosql.data.repo.dao;

import java.util.List;

import com.ucp.nosql.model.Competency;
import com.ucp.nosql.model.CompanyCulture;
import com.ucp.nosql.model.RoleRequirementSlider;

public interface CompetencyNoSqlDAO {
	void saveCompetency(Competency collabParticipant);
	Competency getCompetencyById(String id);
	Competency getCompetencyByCollabParticipantId(String collabId, String participantId);
	List<Competency> findAllCompetencies(int page, int limit);
	void removeCompetencyById(String id);
	void saveCompanyCulture(CompanyCulture companyCulture);
	void saveRoleRequirementSlider(RoleRequirementSlider roleRequirementSlider);
}
