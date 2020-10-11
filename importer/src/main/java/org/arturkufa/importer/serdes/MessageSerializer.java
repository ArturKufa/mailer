package org.arturkufa.importer.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.MapFunction;
import org.arturkufa.importer.model.Message;

public class MessageSerializer implements MapFunction<String, Message> {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Message map(String value) throws Exception {
        return objectMapper.readValue(value, Message.class);
    }
}
