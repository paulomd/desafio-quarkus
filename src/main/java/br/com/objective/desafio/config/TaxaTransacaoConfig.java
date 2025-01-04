package br.com.objective.desafio.config;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.com.objective.desafio.domain.FormaPagamento;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaxaTransacaoConfig {

	@ConfigProperty(name = "percentual.taxa.transacao")
    Map<String, BigDecimal> taxas;

    public Optional<BigDecimal> getTaxa(FormaPagamento formaPagamento) {
    	BigDecimal taxa = taxas.get(formaPagamento.name());
        return Optional.ofNullable(taxa);
    }
}
