package org.arturkufa.mailer.persistance;

import org.arturkufa.mailer.persistance.model.Mail;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MailRepositoryImpl implements MailRepository {
    private List<Mail> inMemoryStorarage = new ArrayList<>();

    @Override
    public void saveMail(Mail mail) {
        inMemoryStorarage.add(mail);
    }

    @Override
    public Flux<Mail> getMailByUserId(Long userId) {
        return Flux.fromIterable(inMemoryStorarage.stream()
                                                    .filter(m -> m.getUserFromId().equals(userId))
                                                    .collect(Collectors.toList()));
    }
}
