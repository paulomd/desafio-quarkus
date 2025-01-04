package br.com.objective.desafio.resource;

import br.com.objective.desafio.dto.ContaDTO;
import br.com.objective.desafio.dto.TransacaoDTO;
import br.com.objective.desafio.service.TransacaoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/transacao")
public class TransacaoResource {
	
	@Inject
	TransacaoService service;

	@POST
	public Response criarConta(@Valid TransacaoDTO transacaoDTO) {
		ContaDTO contaDTO = service.criarTransacao(transacaoDTO);
		return Response.status(Status.CREATED).entity(contaDTO).build();
	}

}
