package br.com.api.projetoaudax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ProjetoaudaxApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoaudaxApplication.class, args);
	}

}
