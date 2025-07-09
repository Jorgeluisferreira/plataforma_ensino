package grupo1.aps2.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class ExceptionUtil {
    public static void throwException(int status, String mensagem) throws WebApplicationException {
        throw new WebApplicationException(
                Response.status(status)
                        .entity(new MensagemErro(mensagem))
                        .type("application/json")
                        .build()
        );
    }

    public static WebApplicationException createWebApplicationException(int status, String mensagem) {
        return new WebApplicationException(
                Response.status(status)
                        .entity(new MensagemErro(mensagem))
                        .type("application/json")
                        .build()
        );
    }
}
