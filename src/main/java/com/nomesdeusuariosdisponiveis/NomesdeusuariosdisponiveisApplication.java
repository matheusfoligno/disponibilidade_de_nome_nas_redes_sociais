package com.nomesdeusuariosdisponiveis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class NomesdeusuariosdisponiveisApplication {

	public static void main(String[] args) {
		SpringApplication.run(NomesdeusuariosdisponiveisApplication.class, args);
	}

}
