package org.arturkufa.exporter.generator;



import org.arturkufa.exporter.generator.model.Message;
import org.arturkufa.exporter.generator.model.User;
import org.arturkufa.exporter.generator.service.MessagesBaseServiceImpl;
import org.arturkufa.exporter.generator.service.UserBaseServiceImpl;
import org.arturkufa.exporter.kafka.Producer;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Generator {
    public void generateKafkaEvents() {
        UserBaseServiceImpl userBaseService = new UserBaseServiceImpl();
        MessagesBaseServiceImpl messagesBaseService = new MessagesBaseServiceImpl();
        Producer kafkaProducer = new Producer();
        ArrayList<User> userBase = userBaseService.generateUserBase(100); //w parametrze podajesz ilu chcesz użytkowników w generowanej bazie
        ArrayList<Message> messageBase = messagesBaseService.generateMessageBase(2000, userBase); //ile ma zrobić wiadomości

       new CompletableFuture().runAsync(() -> kafkaProducer.procudeUser(userBase));
        CompletableFuture<Void> voidCompletableFuture = new CompletableFuture().runAsync(() -> kafkaProducer.produceMessage(messageBase));
        try {
            voidCompletableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
