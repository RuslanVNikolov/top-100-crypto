package io.ruslan.top100crypto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class Top100CryptoApplication {
	public static void main(String[] args) {
		SpringApplication.run(Top100CryptoApplication.class, args);
	}

}
