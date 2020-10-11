package org.arturkufa.exporter.generator.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;

public class FileConverterImpl implements FileConverter {
    @Override
    public ArrayList<String> covertFileToArrayList(String source) {
        ArrayList<String> convertedFile = new ArrayList<>();
        try {
            File file = new File("/home/artur/IdeaProjects/mailer/exporter/src/main/resources/" + source);
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String row;

            while ((row = bufferedReader.readLine()) != null) {
                convertedFile.add(row);
            }
            bufferedReader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
            return convertedFile;
        }
    }

