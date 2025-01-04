package br.com.objective.desafio.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.objective.desafio.domain.FormaPagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TransacaoDTO(
		@NotNull
	    @JsonProperty("forma_pagamento")
		FormaPagamento formaPagamento,
		
		@NotNull
	    @JsonProperty("numero_conta")
	    Long numeroConta,

	    @NotNull
	    @PositiveOrZero
	    BigDecimal valor
	) {}
