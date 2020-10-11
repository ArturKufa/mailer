package org.arturkufa.exporter;

import org.arturkufa.exporter.generator.Generator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExporterApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ExporterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("PRODUCER STARTED");
		Generator generator = new Generator();
		generator.generateKafkaEvents();
		System.out.println("PRODUCER FINISHED");
	}

}
