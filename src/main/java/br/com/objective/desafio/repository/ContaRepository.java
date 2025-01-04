package br.com.objective.desafio.repository;

import java.util.Optional;

import br.com.objective.desafio.entity.Conta;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;

@ApplicationScoped
public class ContaRepository implements PanacheRepository<Conta> {
	public Optional<Conta> buscarComBloqueio(Long numeroConta) {
		EntityManager em = getEntityManager();
		Conta conta = em.find(Conta.class, numeroConta, LockModeType.PESSIMISTIC_WRITE);
		return Optional.ofNullable(conta);
	}
}
