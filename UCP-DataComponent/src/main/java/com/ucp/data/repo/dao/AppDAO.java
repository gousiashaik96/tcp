package com.ucp.data.repo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AppDAO {
	List<Map<String, Object>> getQueryResponse(String query, HashMap<String, Object> queryParams);
}
