package br.com.objective.desafio.exception;

import jakarta.ws.rs.core.Response;

public class ContaDuplicadaException extends ObjectiveException {

	private static final long serialVersionUID = 1L;

	public ContaDuplicadaException(Long numeroConta) {
		super(Response.Status.BAD_REQUEST, "A conta com número " + numeroConta + " já existe.");
	}

}
