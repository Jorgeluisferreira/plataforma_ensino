package grupo1.aps2;

import grupo1.aps2.dto.DTOPayment;
import grupo1.aps2.dto.DTOPayment.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

import java.awt.*;

@ApplicationScoped
@Path("payment")
public class PaymentController {

    @POST
    @Path("/pay")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<PaymentConfirmationDTO> processPayment(PaymentRequestDTO request) {

        return RestResponse.ResponseBuilder.ok(new PaymentConfirmationDTO(), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/refund")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<RefundConfirmationDTO> refundPayment(RefundRequestDTO request) {

        return RestResponse.ResponseBuilder.ok(new RefundConfirmationDTO(), MediaType.APPLICATION_JSON).build();
    }
}
