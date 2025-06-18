package grupo1.aps2.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class ExceptionUtil {
    public static void throwException(int status, String mensagem) {
        throw new WebApplicationException(
                Response.status(status)
                        .entity(new MensagemErro(mensagem))
                        .build()
        );
    }
}
