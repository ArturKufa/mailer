package org.arturkufa.mailer;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MailerSearchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MailerSearchApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.printf("Starting with time delay: %s ms.\n", args[0]);
	}
}
