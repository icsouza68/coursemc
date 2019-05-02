package com.ivansouza.coursemc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ivansouza.coursemc.services.S3Service;

@SpringBootApplication
public class CoursemcApplication implements CommandLineRunner{

	@Autowired
	private S3Service s3Service;   // apenas para testar o upload de imagem
	
	public static void main(String[] args) {
		SpringApplication.run(CoursemcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		s3Service.uploadFile("c:\\temp\\fotos\\ana.jpg");  // arquivo de exemplo apenas
	}

}
