package com.ivansouza.coursemc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.ivansouza.coursemc.domain.Cliente;
import com.ivansouza.coursemc.dto.ClienteDTO;
import com.ivansouza.coursemc.repositories.ClienteRepository;
import com.ivansouza.coursemc.resources.exceptions.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private HttpServletRequest request;

	@Override
	public void initialize(ClienteUpdate ann) {
	}
	
	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id")); 
				
		List<FieldMessage> list = new ArrayList<>();
// inclua os testes aqui, inserindo erros na lista
		
		Cliente aux = repo.findByEMail(objDto.geteMail());
		
		if (aux != null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("eMail", "Email já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}