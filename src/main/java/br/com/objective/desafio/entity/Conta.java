package br.com.objective.desafio.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Conta {
	
	@Id
	@Column(name = "numero_conta", nullable = false, updatable = false)
    private Long numeroConta;

	@Column(name = "saldo", nullable = false)
    private BigDecimal saldo;
	
	public Conta() {
	
	}

	public Conta(Long numeroConta, BigDecimal saldo) {
		super();
		this.numeroConta = numeroConta;
		this.saldo = saldo;
	}

	public Long getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(Long numeroConta) {
		this.numeroConta = numeroConta;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
}
