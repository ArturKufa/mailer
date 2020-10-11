package org.arturkufa.exporter.generator.service;

import org.arturkufa.exporter.generator.model.Message;

import org.arturkufa.exporter.generator.model.User;

import java.util.ArrayList;

public class MessagesBaseServiceImpl implements MessageBaseService {
    private final Randomizer randomizer = new RandomizerImpl();
    private final FileConverter fileConverter = new FileConverterImpl();
    @Override
    public ArrayList<Message> generateMessageBase(int numberOfPositions, ArrayList<User> users) {
        ArrayList<Message> messagesBase= new ArrayList<>();
        ArrayList<String> listOfMessages = fileConverter.covertFileToArrayList("words.txt");
        for (int y = 0; y < (numberOfPositions + 1); y++) {
            Message msg = new Message();
            msg.setMessageId(y);
            msg.setSenderId(randomizer.pickRandomId(users));
            msg.setReceiverId(randomizer.pickRandomId(users)); //todo na przyszłość zmienić, żeby nie wysyłali sami do siebie maili
            msg.setMessageText(randomizer.pickRandomPosition(listOfMessages));
            messagesBase.add(msg);
        }
        return messagesBase;
    }
}
