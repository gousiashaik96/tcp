package com.ucp.nosql.model.log;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "kf_ucp_log")
public class LogTransaction {
	@Id
	private String id;
	private String transactionId;
	private int statusCode;
	private String transactionType;
	private long duration;
	private String URL;
	private String requestData;
	private String responseData;
	private Map<String, String> headers;
	private String authToken;
	private long requestTimeStamp;
	private long responseTimeStamp;
	private String serverId;
	private String method;
	private String transactionRefId;

	public LogTransaction() {
		super();
	}
	
}
