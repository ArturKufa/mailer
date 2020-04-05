package org.arturkufa.mailer.service;

import org.arturkufa.mailer.api.model.ReadMailResponse;
import org.arturkufa.mailer.api.model.SendMailRequest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MailerService {
    Mono<String> sendMail(SendMailRequest sendMailRequest);
    Flux<ReadMailResponse> readMailsForUserId(Long userId);
}
