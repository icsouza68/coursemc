package com.ivansouza.coursemc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ivansouza.coursemc.domain.Cliente;
import com.ivansouza.coursemc.repositories.ClienteRepository;
import com.ivansouza.coursemc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private EmailService es;
	
	private Random rand = new Random();

	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEMail(email);
		
		if (cliente==null) {
			throw new ObjectNotFoundException("E-mail não encontrado!");
		}
		
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		clienteRepository.save(cliente);
		es.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		
		char vet[] = new char[10];
		for (int i=0;i<10;i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3); // gera inteiro entre 0 e 2
		
		if (opt==0) { // gera um dígito
			return (char) (rand.nextInt(10) + 48); // 48 = código ASCII do zero
		}
		else if (opt==1) { // gera letra maiúscula
			return (char) (rand.nextInt(26) + 65); // 65 = código letra A
		}
		else { // gera letra minúscula
			return (char) (rand.nextInt(26) + 97); // 97 = código letra a
		}
	}

}
