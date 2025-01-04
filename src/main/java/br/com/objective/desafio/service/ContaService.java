package br.com.objective.desafio.service;

import java.math.BigDecimal;

import br.com.objective.desafio.dto.ContaDTO;
import br.com.objective.desafio.entity.Conta;
import br.com.objective.desafio.exception.ContaDuplicadaException;
import br.com.objective.desafio.exception.ContaNaoEncontradaException;
import br.com.objective.desafio.exception.SaldoInsuficienteException;
import br.com.objective.desafio.repository.ContaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ContaService {

	@Inject
	ContaRepository repository;

	@Transactional
	public void criarConta(ContaDTO contaDTO) {
		validarSeContaJaExiste(contaDTO.numeroConta());
		repository.persist(new Conta(contaDTO.numeroConta(), contaDTO.saldo()));
	}
	
	@Transactional
	public ContaDTO subtrairSaldoConta(Long numeroConta, BigDecimal valor) {
		Conta conta = repository.buscarComBloqueio(numeroConta).orElseThrow(()->new ContaNaoEncontradaException(numeroConta));
		validarSaldo(conta, valor);
		conta.setSaldo(conta.getSaldo().subtract(valor));
		return new ContaDTO(numeroConta, conta.getSaldo());
	}

	private void validarSaldo(Conta conta, BigDecimal valor) {
		if (valor.compareTo(conta.getSaldo()) > 0) {
            throw new SaldoInsuficienteException(conta.getNumeroConta());
        }
	}

	private void validarSeContaJaExiste(Long numeroConta) {
		repository.findByIdOptional(numeroConta).ifPresent(conta -> {
			throw new ContaDuplicadaException(numeroConta);
		});
	}

	public ContaDTO buscarEValidarExistenciaConta(Long numeroConta) {
		return repository.findByIdOptional(numeroConta)
				.map(conta -> new ContaDTO(numeroConta, conta.getSaldo()))
				.orElseThrow(()->new ContaNaoEncontradaException(numeroConta));
	}
}
