package grupo1.aps2.exceptions.mapper;

import grupo1.aps2.exceptions.ErroResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class ConstraintValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        Map<String, String> messages = new HashMap<>();

        if (e.getConstraintViolations() != null) {
            for (ConstraintViolation<?> c : e.getConstraintViolations()) {
                messages.put(c.getPropertyPath().toString(), c.getMessage());
            }
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErroResponse("Erros de validação", messages.values()))
                .build();
    }
}
