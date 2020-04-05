package org.arturkufa.mailer.service;

import lombok.AllArgsConstructor;
import org.arturkufa.mailer.api.model.ReadMailResponse;
import org.arturkufa.mailer.api.model.SendMailRequest;
import org.arturkufa.mailer.persistance.MailRepository;
import org.arturkufa.mailer.persistance.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class MailerServiceImpl implements MailerService {
    @Autowired
    private MailRepository mailRepository;

    @Override
    public Mono<String> sendMail(SendMailRequest sendMailRequest) {

        mailRepository.saveMail(Mail.builder().userFromId(sendMailRequest.getUserFromId())
                                                    .userToId(sendMailRequest.getUserToId())
                                                    .topic(sendMailRequest.getTopic())
                                                    .body(sendMailRequest.getBody())
                                                    .timestamp(LocalDateTime.now())
                                                    .build());
        return Mono.just("Mail has been send.");
    }

    @Override
    public Flux<ReadMailResponse> readMailsForUserId(Long userId) {
        return mailRepository.getMailByUserId(userId)
                            .map(m -> ReadMailResponse.builder().userFromId(m.getUserFromId())
                            .timestamp(m.getTimestamp())
                            .topic(m.getTopic())
                            .body(m.getBody())
                            .build());
    }


}
