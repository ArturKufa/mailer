package org.arturkufa.mailer.api;

import org.arturkufa.mailer.ArgHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping(path = "/search/v1")
public class MailerApi {

    @Autowired
    private ArgHolder argHolder;
    @GetMapping(path = "/", produces = "application/json")
    public Flux<String> readMail(@RequestParam(value = "q") Long query) {
        try {
            Thread.sleep(argHolder.getTimeout());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Flux.just("ID: " + argHolder.getId() + " , timeout: " + argHolder.getTimeout());
    }

}
