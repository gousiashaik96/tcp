package com.ucp.nosql.data.repo.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ucp.nosql.data.repo.dao.CompetencyNoSqlDAO;
import com.ucp.nosql.model.Competency;
import com.ucp.nosql.model.CompanyCulture;
import com.ucp.nosql.model.RoleRequirementSlider;

@Repository
public class CompetencyNoSqlDAOImpl implements CompetencyNoSqlDAO {

	@Autowired
	MongoTemplate mongoTemplate;


	@Override
	public Competency getCompetencyById(String id) {
		// TODO Auto-generated method stub
		ObjectId objectId = new ObjectId(id);
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(objectId));
		return mongoTemplate.findOne(query, Competency.class);
	}

	@Override
	public List<Competency> findAllCompetencies(int page, int limit) {
		// TODO Auto-generated method stub
		Query query = new Query();
		Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("collabId").ascending());
		query.with(pageable);
		return mongoTemplate.find(query, Competency.class);
	}

	@Override
	public void removeCompetencyById(String id) {
		// TODO Auto-generated method stub
		ObjectId objectId = new ObjectId(id);
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(objectId));
		mongoTemplate.remove(query, Competency.class);
	}

	@Override
	public void saveCompanyCulture(CompanyCulture companyCulture) {
		mongoTemplate.save(companyCulture);
		
	}

	@Override
	public void saveRoleRequirementSlider(RoleRequirementSlider roleRequirementSlider) {
		mongoTemplate.save(roleRequirementSlider);
	}

	@Override
	public void saveCompetency(Competency competency) {
		mongoTemplate.save(competency);
		
	}

	@Override
	public Competency getCompetencyByCollabParticipantId(String collabId, String participantId) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where("collabId").is(collabId).and("participantId").is(participantId));
		return mongoTemplate.findOne(query, Competency.class);
	}

	
}
