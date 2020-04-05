package org.arturkufa.mailer.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Value
public class SendMailRequest {
    @NotNull
    private Long userFromId;
    @NotNull
    private Long userToId;
    @NotNull
    @Size(max = 100)
    private String topic;
    @NotNull
    @Size(max = 512)
    private String body;
}
