package org.arturkufa.exporter.generator.service;

import org.arturkufa.exporter.generator.model.Message;
import org.arturkufa.exporter.generator.model.User;

import java.util.ArrayList;

public interface MessageBaseService {
    ArrayList<Message> generateMessageBase(int numberOfPositions, ArrayList<User> users);
}
