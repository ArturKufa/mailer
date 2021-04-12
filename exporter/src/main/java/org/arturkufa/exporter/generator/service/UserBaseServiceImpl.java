package org.arturkufa.exporter.generator.service;


import org.arturkufa.exporter.generator.model.User;

import java.util.ArrayList;

public class UserBaseServiceImpl implements UserBaseService {
    private final RandomizerImpl randomizer = new RandomizerImpl();
    private final FileConverter fileConverter = new FileConverterImpl();

    @Override
    public ArrayList<User> generateUserBase(int numberOfPositions) {
        ArrayList<User> userBase = new ArrayList<>();
        ArrayList<String> firstNames = fileConverter.covertFileToArrayList("first_names.txt");
        ArrayList<String> lastNames = fileConverter.covertFileToArrayList("last_names.txt");
        for (int y = 0; y < (numberOfPositions + 1); y++) {
            User user = new User();
            user.setId(y);
            user.setFirstName(randomizer.pickRandomPosition(firstNames));
            user.setLastName(randomizer.pickRandomPosition(lastNames));
            userBase.add(user);
        }
        return userBase;
    }
}
