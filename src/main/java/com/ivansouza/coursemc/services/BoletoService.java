package com.ivansouza.coursemc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ivansouza.coursemc.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7); // Vencimento 7 dias após a data do pedido
		pagto.setDataVencimento(cal.getTime());
	}
}
