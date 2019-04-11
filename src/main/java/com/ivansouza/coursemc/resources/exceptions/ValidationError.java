package com.ivansouza.coursemc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private List<FieldMessage> errors = new ArrayList<FieldMessage>();
	
	public ValidationError(Integer status, String msg, Long timestamp) {
		super(status, msg, timestamp);
		// TODO Auto-generated constructor stub
	}

	public List<FieldMessage> getError() {
		return errors;
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}

}
