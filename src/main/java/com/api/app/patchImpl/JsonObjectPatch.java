package com.api.app.patchImpl;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.flipkart.zjsonpatch.JsonPatch;

public class JsonObjectPatch implements ObjectPatch {

	private final ObjectMapper objectMapper;
	private final JsonNode patch;
	
	public JsonObjectPatch(ObjectMapper objectMapper, JsonNode patch) {
	    this.objectMapper = objectMapper;
	    this.patch = patch;
	}
	
	@Override
	public <T> T apply(T target) throws ObjectPatchException {
		JsonNode source = objectMapper.valueToTree(target);
	    JsonNode result;
	    try {
		    result = JsonPatch.apply(patch, source);
		    ObjectReader reader = objectMapper.readerForUpdating(target);
		    reader.readValue(result);
			return reader.readValue(result);
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			throw new ObjectPatchException();
		}
	}

}
