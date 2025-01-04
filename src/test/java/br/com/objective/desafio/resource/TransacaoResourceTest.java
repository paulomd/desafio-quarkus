package br.com.objective.desafio.resource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import br.com.objective.desafio.domain.FormaPagamento;
import br.com.objective.desafio.dto.ContaDTO;
import br.com.objective.desafio.dto.TransacaoDTO;
import br.com.objective.desafio.entity.Conta;
import br.com.objective.desafio.repository.ContaRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@QuarkusTest
class TransacaoResourceTest {
	
	private static final Long NUMERO_CONTA = 1l;
	private static final BigDecimal VALOR = BigDecimal.ONE;
	private static final BigDecimal SALDO = BigDecimal.TEN;
	
	@InjectMock
	ContaRepository repository;
	
	@Test
	void deveDarErroAoTransacionarSeFormaPagamentoForNulo() {
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(new TransacaoDTO(null, NUMERO_CONTA, VALOR))
		.when().post("/transacao")
		.then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());
	}
	
	@Test
	void deveDarErroAoTransacionarSeNumeroContaForNulo() {
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(new TransacaoDTO(FormaPagamento.P, null, VALOR))
		.when().post("/transacao")
		.then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());
	}
	
	@Test
	void deveDarErroAoTransacionarSeValorForNulo() {
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(new TransacaoDTO(FormaPagamento.P, NUMERO_CONTA, null))
		.when().post("/transacao")
		.then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());
	}

	@Test
	void deveDarErroAoTransacionarSeContaNaoExistir() {
		when(repository.buscarComBloqueio(NUMERO_CONTA)).thenReturn(Optional.empty());
		
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(new TransacaoDTO(FormaPagamento.P, NUMERO_CONTA, VALOR))
		.when().post("/transacao")
		.then().statusCode(Response.Status.NOT_FOUND.getStatusCode());
		
		verify(repository).buscarComBloqueio(NUMERO_CONTA);
	}
	
	@Test
	void deveDarErroAoTransacionarSeSaldoForInsuficiente() {
		when(repository.buscarComBloqueio(NUMERO_CONTA)).thenReturn(Optional.of(new Conta(NUMERO_CONTA, BigDecimal.ZERO)));
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(new TransacaoDTO(FormaPagamento.P, NUMERO_CONTA, VALOR))
		.when().post("/transacao")
		.then().statusCode(Response.Status.NOT_FOUND.getStatusCode());
		
		verify(repository).buscarComBloqueio(NUMERO_CONTA);
	}
	
	@Test
	void deveTransacionarPix() {
		when(repository.buscarComBloqueio(NUMERO_CONTA)).thenReturn(Optional.of(new Conta(NUMERO_CONTA, SALDO)));
		
		ContaDTO contaDTO = given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(new TransacaoDTO(FormaPagamento.P, NUMERO_CONTA, VALOR))
		.when().post("/transacao")
		.then().statusCode(Response.Status.CREATED.getStatusCode())
		.extract().body().as(ContaDTO.class);
		
		assertEquals(NUMERO_CONTA, contaDTO.numeroConta());
		assertEquals(new BigDecimal(9), contaDTO.saldo());
		verify(repository).buscarComBloqueio(NUMERO_CONTA);
	}
	
	@Test
	void deveTransacionarDebito() {
		when(repository.buscarComBloqueio(NUMERO_CONTA)).thenReturn(Optional.of(new Conta(NUMERO_CONTA, SALDO)));
	
		ContaDTO contaDTO = given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(new TransacaoDTO(FormaPagamento.D, NUMERO_CONTA, VALOR))
		.when().post("/transacao")
		.then().statusCode(Response.Status.CREATED.getStatusCode())
		.extract().body().as(ContaDTO.class);
		
		assertEquals(NUMERO_CONTA, contaDTO.numeroConta());
		assertEquals(new BigDecimal("8.97"), contaDTO.saldo());
		verify(repository).buscarComBloqueio(NUMERO_CONTA);
	}
	
	@Test
	void deveTransacionarCredito() {
		when(repository.buscarComBloqueio(NUMERO_CONTA)).thenReturn(Optional.of(new Conta(NUMERO_CONTA, SALDO)));
		
		ContaDTO contaDTO = given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(new TransacaoDTO(FormaPagamento.C, NUMERO_CONTA, VALOR))
		.when().post("/transacao")
		.then().statusCode(Response.Status.CREATED.getStatusCode())
		.extract().body().as(ContaDTO.class);
		
		assertEquals(NUMERO_CONTA, contaDTO.numeroConta());
		assertEquals(new BigDecimal("8.95"), contaDTO.saldo());
		verify(repository).buscarComBloqueio(NUMERO_CONTA);
	}
}
