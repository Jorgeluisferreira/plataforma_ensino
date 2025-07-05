package grupo1.aps2.exceptions;

import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

public class UnauthorizedUserException extends ClientWebApplicationException {
  public UnauthorizedUserException(String message) {
    super(Response
            .status(Response.Status.UNAUTHORIZED)
            .entity(message)
            .build());
  }
}
