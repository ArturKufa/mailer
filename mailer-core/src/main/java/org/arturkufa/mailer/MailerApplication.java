package org.arturkufa.mailer;

import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.io.BufferedReader;

@EnableEurekaClient
@SpringBootApplication
public class MailerApplication implements CommandLineRunner {

	@Value("${server.port}")
	String port;
	public static void main(String[] args) {
		SpringApplication.run(MailerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("IM WORKING, service.port=" + port);

	}
}
