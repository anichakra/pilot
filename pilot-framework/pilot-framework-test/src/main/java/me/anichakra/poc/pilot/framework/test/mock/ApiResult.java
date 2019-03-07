package me.anichakra.poc.pilot.framework.test.mock;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiResult<T> {
	ObjectMapper mapper;

	public ApiResult(ResultActions resultActions) {
		this.resultActions = resultActions;
		 mapper = new ObjectMapper();
	}

	private ResultActions resultActions;
	

	ResultActions getResultActions() {
		return resultActions;
	}

	public T getResultBean(Class<T> clazz) throws JsonParseException, JsonMappingException, UnsupportedEncodingException, IOException {
		return mapper.readValue(getJsonResponseAsString(), clazz);
	}
	
	public String getJsonResponseAsString() throws UnsupportedEncodingException {
		return resultActions.andReturn().getResponse().getContentAsString();
	}

}
