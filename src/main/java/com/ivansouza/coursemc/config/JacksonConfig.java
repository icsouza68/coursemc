package com.ivansouza.coursemc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivansouza.coursemc.domain.PagamentoComBoleto;
import com.ivansouza.coursemc.domain.PagamentoComCartao;

// A anotação @configuration indica que essa é uma classe de configuração, que será executada logo no início
// Essa é uma classe padrão, exigida pela biblioteca, e que deve ser configurada com as subclasses a serem registradas
@Configuration
public class JacksonConfig {
// https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare
	// A anotação @Bean indica ser um método de configuração
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				// Abaixo estão as subclasse que precisam ser registradas
				// Ver a anotação @Type em cada uma delas
				objectMapper.registerSubtypes(PagamentoComCartao.class);
				objectMapper.registerSubtypes(PagamentoComBoleto.class);
				super.configure(objectMapper);
			};
		};
		return builder;
	}
}