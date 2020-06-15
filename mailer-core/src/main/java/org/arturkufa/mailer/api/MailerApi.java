package org.arturkufa.mailer.api;

import org.arturkufa.mailer.api.model.ReadMailResponse;
import org.arturkufa.mailer.api.model.SendMailRequest;
import org.arturkufa.mailer.service.MailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/mailer/v1")
public class MailerApi {
    @Autowired
    private MailerService mailerService;

    //todo Handle exceptions with ResponseEntity or ErrorHandler.
    @GetMapping(path = "/read", produces = "application/json")
    public Flux<ReadMailResponse> readMail(@RequestParam(value = "userId") Long userId) {
        return mailerService.readMailsForUserId(userId);
    }

    @PostMapping(path = "/send", consumes = "application/json")
    public  Mono<String> sendMail(@RequestBody @Valid SendMailRequest sendMailRequest) {
        return mailerService.sendMail(sendMailRequest);
    }
}
