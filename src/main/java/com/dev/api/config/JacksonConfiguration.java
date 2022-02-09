package com.dev.api.config;

import javax.annotation.PostConstruct;

import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfiguration {

	private ObjectMapper objectMapper;

	public JacksonConfiguration(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@PostConstruct
	public ObjectMapper jacksonObjectMapper() {
		objectMapper.registerModule(new JsonNullableModule());
		return objectMapper;
	}
}
