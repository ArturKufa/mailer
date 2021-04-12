package org.arturkufa.exporter.generator.service;


import org.arturkufa.exporter.generator.model.User;

import java.util.ArrayList;

public interface Randomizer {
    String pickRandomPosition(ArrayList<String> positionsToRandom);
    Integer pickRandomId(ArrayList<User> base);
}
