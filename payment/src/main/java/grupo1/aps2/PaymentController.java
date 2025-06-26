package grupo1.aps2;

import grupo1.aps2.dto.DTOPayment.PaymentConfirmationDTO;
import grupo1.aps2.dto.DTOPayment.PaymentRequestDTO;
import grupo1.aps2.dto.DTOPayment.RefundConfirmationDTO;
import grupo1.aps2.dto.DTOPayment.RefundRequestDTO;
import grupo1.aps2.mapper.PaymentMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

@ApplicationScoped
@Path("payment")
public class PaymentController {

    @Inject
    PaymentService paymentService;

    @Inject
    PaymentMapper paymentMapper;

    @POST
    @Path("/pay")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<PaymentConfirmationDTO> processPayment(
            PaymentRequestDTO request,
            @HeaderParam("Authorization") String authorizationHeader) {

        request.setUserInfo(paymentMapper.fromJwt(validateUserInfo(authorizationHeader)));

        return RestResponse.ResponseBuilder.ok(paymentService.processPayment(request), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/refund")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<RefundConfirmationDTO> refundPayment(
            RefundRequestDTO request,
            @HeaderParam("Authorization") String authorizationHeader) {

        return RestResponse.ResponseBuilder.ok(paymentService.refundPayment(request), MediaType.APPLICATION_JSON).build();
    }

    private String validateUserInfo(String jwt) {
        String token = null;
        if (jwt != null && jwt.startsWith("Bearer ")) {
            token = jwt.substring(7);
        } else {
            throw new IllegalArgumentException("Autenticação inválida: JWT não fornecido ou inválido.");
        }

        return token;
    }
}
