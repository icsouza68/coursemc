package com.ivansouza.coursemc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ivansouza.coursemc.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;
	
	// A notação abaixo (Value) joga o valor da chave dentro da variável strategy
	// Se o valor dessa variável for 'create', o banco de dados deve ser criado
	// caso contrário, não deve ser criado.
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		if (!"create".equals(strategy)) {
			return false;
		}
		dbService.InstantiateDatabase();
		return true;
	}
}
