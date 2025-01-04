package br.com.objective.desafio.exception;

import jakarta.ws.rs.core.Response;

public class SaldoInsuficienteException extends ObjectiveException {

	private static final long serialVersionUID = 1L;

	public SaldoInsuficienteException(Long numeroConta) {
		super(Response.Status.NOT_FOUND, "A conta com número " + numeroConta + " não possui saldo suficiente para a transação.");
	}
}
