package org.arturkufa.mailer.persistance.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
public class Mail {
    private Long id;
    private Long userFromId;
    private Long userToId;
    private String topic;
    private String body;
    private LocalDateTime timestamp;
}
