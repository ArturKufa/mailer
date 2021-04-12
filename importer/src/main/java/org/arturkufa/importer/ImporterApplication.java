package org.arturkufa.importer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImporterApplication implements CommandLineRunner {

	@Autowired
	FlinkTableImporter flinkTableImporter;

	@Autowired
	FlinkMailByUserPrinter keyedStreamImporter;

	public static void main(String[] args) {
		SpringApplication.run(ImporterApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
//		flinkTableImporter.getMailDataSet();
		keyedStreamImporter.getMailDataSet();
	}
}
