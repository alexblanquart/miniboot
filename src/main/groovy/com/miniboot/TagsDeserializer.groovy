package com.miniboot

import java.io.IOException
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer

class TagsDeserializer extends JsonDeserializer<List<Tag>> {

	@Override
	public List<Tag> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		def tags = []
		p.readValueAs(new TypeReference<List<String>>() { }).each {
			tags << new Tag(name:it)
		}
		tags
	}

}
