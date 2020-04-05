package org.arturkufa.mailer.persistance;

import org.arturkufa.mailer.persistance.model.Mail;
import reactor.core.publisher.Flux;

public interface MailRepository {

    void saveMail(Mail mail);
    Flux<Mail> getMailByUserId(Long userId);

}
