package br.com.objective.desafio.exception;

import jakarta.ws.rs.core.Response;

public class ContaNaoEncontradaException extends ObjectiveException {

	private static final long serialVersionUID = 1L;

	public ContaNaoEncontradaException(Long numeroConta) {
		super(Response.Status.NOT_FOUND, "A conta com número " + numeroConta + " não foi encontrada.");
	}

}
