package org.arturkufa.mailer.api;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping(path = "/search/v1")
public class MailerApi {

    //todo Handle exceptions with ResponseEntity or ErrorHandler.
    @GetMapping(path = "/", produces = "application/json")
    public Flux<String> readMail(@RequestParam(value = "q") Long query) {
        return Flux.just("searching...");
    }

}
