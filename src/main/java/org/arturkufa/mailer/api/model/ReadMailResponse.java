package org.arturkufa.mailer.api.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Value
public class ReadMailResponse {
    private Long userFromId;
    private String topic;
    private String body;
    private LocalDateTime timestamp;
}
