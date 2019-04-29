package com.ivansouza.coursemc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	private static final String[] PUBLIC_MATCHERS = {
		"/h2-console/**",
	};
	
	// caminhos apenas de leitura
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/clientes/**",
			"/categorias/**"
		};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// Comando específico para usar o banco de dados H2
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		http.cors().and().csrf().disable();
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	// o bean abaixo faz parte da solução que o instrutor pegou na internet
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
/*
 "Adds a CorsFilter to be used. If a bean by the name of corsFilter is provided, that CorsFilter is
used. Else if corsConfigurationSource is defined, then that CorsConfiguration is used. Otherwise, if
Spring MVC is on the classpath a HandlerMappingIntrospector is used."
 */
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
