package grupo1.aps2.exceptions;

import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

public class BadRequestException extends ClientWebApplicationException {
    public BadRequestException(String message) {
        super(Response
                .status(Response.Status.BAD_REQUEST)
                .entity(message)
                .build());
    }
}
