package br.com.objective.desafio.provider;

import br.com.objective.desafio.exception.ObjectiveException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ObjectiveExceptionMapper implements ExceptionMapper<ObjectiveException> {

    @Override
    public Response toResponse(ObjectiveException exception) {
        return Response.status(exception.getStatus())
                .entity(exception.getMessage())
                .build();
    }
}
