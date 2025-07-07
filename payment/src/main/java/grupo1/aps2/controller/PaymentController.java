package grupo1.aps2.controller;

import grupo1.aps2.dto.DTOPayment.*;
import grupo1.aps2.mapper.PaymentMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Consumes;
import org.jboss.resteasy.reactive.RestResponse;
import grupo1.aps2.strategy.*;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("payment")
public class PaymentController {

    @POST
    @Path("/pay")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response processPayment(PaymentRequestDTO paymentRequestDTO) {
        String paymentMethod = paymentRequestDTO.getPaymentMethod();

        PaymentStrategy strategy;
        switch (paymentMethod.toLowerCase()) {
            case "credit":
                strategy = new CreditCardPayment();
                break;
            case "debit":
                strategy = new DebitCardPayment();
                break;
            case "pix":
                strategy = new PixPayment();
                break;
            default:
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid payment method")
                        .build();
        }

        String result = strategy.processPayment(paymentRequestDTO);

        PaymentResponseDTO responseDTO = new PaymentResponseDTO(
                "Payment processed successfully",
                paymentRequestDTO.getPaymentMethod(),
                paymentRequestDTO.getUserEmail(),
                paymentRequestDTO.getAmount(),
                result
        );
        responseDTO.setMessage("Payment processed successfully");
        responseDTO.setPaymentMethod(paymentRequestDTO.getPaymentMethod());
        responseDTO.setUserEmail(paymentRequestDTO.getUserEmail());
        responseDTO.setAmount(paymentRequestDTO.getAmount());
        responseDTO.setStatus(result);

        return Response.ok(responseDTO).build();
    }

}
