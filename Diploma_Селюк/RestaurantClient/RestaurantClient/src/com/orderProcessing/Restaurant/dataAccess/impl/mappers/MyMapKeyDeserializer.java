package com.orderProcessing.Restaurant.dataAccess.impl.mappers;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderProcessing.Restaurant.model.Client;

public class MyMapKeyDeserializer extends KeyDeserializer {
    private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
		return mapper.readValue(key, Client.class);
	}

}
