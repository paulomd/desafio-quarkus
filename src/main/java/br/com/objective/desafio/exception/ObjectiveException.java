package br.com.objective.desafio.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class ObjectiveException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private final Response.Status status;

	public ObjectiveException(Status status, String mensagem) {
		super(mensagem);
		this.status = status;
	}

	public Response.Status getStatus() {
		return status;
	}
}
