package br.com.objective.desafio.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ContaDTO(
		@NotNull
	    @JsonProperty("numero_conta")
	    Long numeroConta,

	    @NotNull
	    @PositiveOrZero
	    BigDecimal saldo
	) {}
