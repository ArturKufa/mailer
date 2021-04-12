package org.arturkufa.importer.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.MapFunction;
import org.arturkufa.importer.model.Mail;

public class MessageSerializer implements MapFunction<String, Mail> {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Mail map(String value) throws Exception {
        return objectMapper.readValue(value, Mail.class);
    }
}
