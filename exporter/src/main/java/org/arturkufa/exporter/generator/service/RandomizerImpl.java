package org.arturkufa.exporter.generator.service;


import org.arturkufa.exporter.generator.model.User;

import java.util.ArrayList;
import java.util.Random;

public class RandomizerImpl implements Randomizer {
    private final Random rand = new Random();

    @Override
    public String pickRandomPosition(ArrayList<String> positionsToRandom) {
        return positionsToRandom.get(rand.nextInt(positionsToRandom.size()));
    }

    @Override
    public Integer pickRandomId(ArrayList<User> base) {
        return rand.nextInt(base.size());
    }
}
