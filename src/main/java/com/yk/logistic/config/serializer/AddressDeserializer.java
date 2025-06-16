package com.yk.logistic.config.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.yk.logistic.domain.address.Address;

import java.io.IOException;

public class AddressDeserializer extends JsonDeserializer<Address> {

	@Override
    public Address deserialize(JsonParser p, DeserializationContext ctxt) 
            throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String street = node.has("street") ? node.get("street").asText() : null;
        String city = node.has("city") ? node.get("city").asText() : null;
        String zipCode = node.has("zipCode") ? node.get("zipCode").asText() : null;
        return new Address(street, city, zipCode);
    }
}