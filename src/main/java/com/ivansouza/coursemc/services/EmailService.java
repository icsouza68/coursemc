package com.ivansouza.coursemc.services;

import org.springframework.mail.SimpleMailMessage;

import com.ivansouza.coursemc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
