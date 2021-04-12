package org.arturkufa.importer.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.MapFunction;
import org.arturkufa.importer.model.User;

public class UserSerializer implements MapFunction<String, User> {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public User map(String value) throws Exception {
        return objectMapper.readValue(value, User.class);
    }
}
