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
import org.jboss.resteasy.reactive.RestResponse;
import grupo1.aps2.strategy.*;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("payment")
public class PaymentController {

//    @Inject
//    PaymentService paymentService;
//
//    @Inject
//    PaymentMapper paymentMapper;
//
//    @POST
//    @Path("/pay")
//    @Produces(MediaType.APPLICATION_JSON)
//    public RestResponse<PaymentConfirmationDTO> processPayment(
//            PaymentRequestDTO request,
//            @HeaderParam("Authorization") String authorizationHeader) {
//
//        request.setUserInfo(paymentMapper.fromJwt(validateUserInfo(authorizationHeader)));
//
//        return RestResponse.ResponseBuilder.ok(paymentService.processPayment(request), MediaType.APPLICATION_JSON).build();
//    }
//
//    @POST
//    @Path("/refund")
//    @Produces(MediaType.APPLICATION_JSON)
//    public RestResponse<RefundConfirmationDTO> refundPayment(
//            RefundRequestDTO request,
//            @HeaderParam("Authorization") String authorizationHeader) {
//
//        return RestResponse.ResponseBuilder.ok(paymentService.refundPayment(request), MediaType.APPLICATION_JSON).build();
//    }
//
//    private String validateUserInfo(String jwt) {
//        String token = null;
//        if (jwt != null && jwt.startsWith("Bearer ")) {
//            token = jwt.substring(7);
//        } else {
//            throw new IllegalArgumentException("Autenticação inválida: JWT não fornecido ou inválido.");
//        }
//
//        return token;
//    }

    @POST
    @Path("/pay")
    public Response processPayment(PaymentRequestDTO request) {
        PaymentStrategy strategy;

        switch (request.getPaymentMethod().toLowerCase()) {
            case "debit":
                strategy = new DebitCardPayment();
                break;
            case "credit":
                strategy = new CreditCardPayment();
                break;
            case "pix":
                strategy = new PixPayment();
                break;
            default:
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid payment method: " + request.getPaymentMethod())
                        .build();
        }

        // Processa o pagamento
        String result = strategy.processPayment(request);

        return Response.ok(result).build();
    }
}
