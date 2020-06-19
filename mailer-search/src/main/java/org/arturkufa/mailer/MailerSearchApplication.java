package org.arturkufa.mailer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MailerSearchApplication implements CommandLineRunner {

	@Autowired
	private ArgHolder argHolder;

	public static void main(String[] args) {
		SpringApplication.run(MailerSearchApplication.class, args);
	}

	@Override
	public void run(String... args) {
		argHolder.setId(Integer.parseInt(args[0]));
		argHolder.setTimeout(Integer.parseInt(args[1]));
		System.out.printf("ID: %s | Starting with time delay: %s ms.\n", args[0], args[1]);
	}
}
