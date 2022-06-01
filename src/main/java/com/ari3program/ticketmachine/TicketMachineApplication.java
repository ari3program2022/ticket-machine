package com.ari3program.ticketmachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@SpringBootApplication
@LineMessageHandler
public class TicketMachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketMachineApplication.class, args);
	}

}
