package br.com.objective.desafio.resource;

import br.com.objective.desafio.dto.ContaDTO;
import br.com.objective.desafio.service.ContaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/conta")
public class ContaResource {
	
	@Inject
	ContaService service;

	@POST
	public Response criarConta(@Valid ContaDTO contaDTO) {
		service.criarConta(contaDTO);
		return Response.status(Status.CREATED).entity(contaDTO).build();
	}
	
	@GET
	public Response buscarConta(@Valid @NotNull @QueryParam("numero_conta") Long numeroConta) {
		ContaDTO contaDTO = service.buscarEValidarExistenciaConta(numeroConta);
		return Response.ok().entity(contaDTO).build();
	}
}
