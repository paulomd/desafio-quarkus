package br.com.objective.desafio.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import br.com.objective.desafio.config.TaxaTransacaoConfig;
import br.com.objective.desafio.dto.ContaDTO;
import br.com.objective.desafio.dto.TransacaoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TransacaoService {
	
	@Inject
	ContaService contaService;
	
	@Inject
	TaxaTransacaoConfig taxaTransacaoConfig;
	
	public ContaDTO criarTransacao(TransacaoDTO transacaoDTO) {
		BigDecimal valorTotal = calcularValorComTaxa(transacaoDTO);
		return contaService.subtrairSaldoConta(transacaoDTO.numeroConta(), valorTotal);
	}

	private BigDecimal calcularValorComTaxa(TransacaoDTO transacaoDTO) {
		Optional<BigDecimal> taxa = taxaTransacaoConfig.getTaxa(transacaoDTO.formaPagamento());
		if(taxa.isEmpty()) {
			return transacaoDTO.valor();
		}
		BigDecimal taxaDecimal = taxa.get().divide(BigDecimal.valueOf(100), 9,RoundingMode.HALF_UP);
        BigDecimal valorAcrescido = transacaoDTO.valor().multiply(taxaDecimal);
        return transacaoDTO.valor().add(valorAcrescido).setScale(2, RoundingMode.HALF_UP);
	}
}
