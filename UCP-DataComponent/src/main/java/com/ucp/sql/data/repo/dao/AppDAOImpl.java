package com.ucp.sql.data.repo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import com.ucp.data.repo.dao.AppDAO;

@Repository
public class AppDAOImpl implements AppDAO {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Map<String, Object>> getQueryResponse(String query, HashMap<String, Object> queryParams) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result = null;
		NativeQueryImpl nativeQuery = (NativeQueryImpl) entityManager.createNativeQuery(query);
		addQueryParameter(nativeQuery, queryParams);
		nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return nativeQuery.getResultList();
	}

	private void addQueryParameter(NativeQueryImpl nativeQuery, HashMap<String, Object> queryParams) {
		if (null != queryParams) {
			queryParams.entrySet().stream().forEach(queryParam -> {
				nativeQuery.setParameter(queryParam.getKey(), queryParam.getValue());
			});
		}
	}

}
