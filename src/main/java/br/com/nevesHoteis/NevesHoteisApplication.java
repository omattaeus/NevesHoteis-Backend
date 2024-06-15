package br.com.nevesHoteis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class NevesHoteisApplication {

	public static void main(String[] args) {

		SpringApplication.run(NevesHoteisApplication.class, args);
		System.out.println(LocalDateTime.now());
	}

}
