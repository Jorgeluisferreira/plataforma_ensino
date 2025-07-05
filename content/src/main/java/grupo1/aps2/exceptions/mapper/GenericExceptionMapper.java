package grupo1.aps2.exceptions.mapper;

import grupo1.aps2.exceptions.MensagemErro;
import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        Log.error("### Ocorreu um erro: ", e);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new MensagemErro("Erro interno, tente mais tarde."))
                .build();
    }
}
