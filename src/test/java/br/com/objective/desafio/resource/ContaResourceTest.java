package br.com.objective.desafio.resource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import br.com.objective.desafio.dto.ContaDTO;
import br.com.objective.desafio.entity.Conta;
import br.com.objective.desafio.repository.ContaRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@QuarkusTest
class ContaResourceTest {

	private static final Long NUMERO_CONTA = 1l;
	private static final BigDecimal SALDO = BigDecimal.TEN;
	
	@InjectMock
	ContaRepository repository;

	@Test
	void deveDarErroAoCriarContaSeSaldoForNulo() {
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(new ContaDTO(NUMERO_CONTA, null))
		.when().post("/conta")
		.then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());
	}
	
	@Test
	void deveDarErroAoCriarContaSeSaldoForNegativo() {
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(new ContaDTO(NUMERO_CONTA, new BigDecimal(-1)))
		.when().post("/conta")
		.then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());
	}
	
	@Test
	void deveDarErroAoCriarContaSeNumeroContaForNulo() {
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(new ContaDTO(null, SALDO))
		.when().post("/conta")
		.then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());
		
		verify(repository, never()).persist(any(Conta.class));
	}

	@Test
	void deveDarErroAoCriarContaSeNumeroContaJaExistir() {
		when(repository.findByIdOptional(NUMERO_CONTA)).thenReturn(Optional.of(new Conta(NUMERO_CONTA, SALDO)));
		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(new ContaDTO(NUMERO_CONTA, SALDO))
		.when().post("/conta")
		.then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());
		
		verify(repository).findByIdOptional(NUMERO_CONTA);
	}
	
	@Test
	void deveCriarContaComSucesso() {
		doNothing().when(repository).persist(any(Conta.class));
		ContaDTO contaDTO = given()
			.contentType(MediaType.APPLICATION_JSON)
			.body(new ContaDTO(NUMERO_CONTA, SALDO))
			.when().post("/conta")
			.then().statusCode(Response.Status.CREATED.getStatusCode())
			.extract().body().as(ContaDTO.class);
		
		assertEquals(NUMERO_CONTA, contaDTO.numeroConta());
		assertEquals(SALDO, contaDTO.saldo());
		verify(repository).persist(any(Conta.class));
	}
	
	@Test
	void deveDarErroAoBuscarContaSeContaNaoExistir() {
		when(repository.findByIdOptional(NUMERO_CONTA)).thenReturn(Optional.empty());
		given()
		    .queryParam("numero_conta", NUMERO_CONTA)
			.when().get("/conta")
			.then().statusCode(Response.Status.NOT_FOUND.getStatusCode());
		
		verify(repository).findByIdOptional(NUMERO_CONTA);
	}
	
	@Test
	void deveDarErroAoBuscarContaSeNumeroContaNulo() {
		given()
			.when().get("/conta")
			.then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());
	}
	
	@Test
	void deveBuscarConta() {
		when(repository.findByIdOptional(NUMERO_CONTA)).thenReturn(Optional.of(new Conta(NUMERO_CONTA, SALDO)));
		ContaDTO contaDTO = given()
		    .queryParam("numero_conta", NUMERO_CONTA)
			.when().get("/conta")
			.then().statusCode(Response.Status.OK.getStatusCode())
			.extract().body().as(ContaDTO.class);
		
		assertEquals(NUMERO_CONTA, contaDTO.numeroConta());
		assertEquals(SALDO, contaDTO.saldo());
		verify(repository).findByIdOptional(NUMERO_CONTA);
	}

}
