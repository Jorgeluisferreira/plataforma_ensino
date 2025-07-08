package grupo1.aps2.strategy;

import grupo1.aps2.dto.DTOPayment.*;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class PaymentContext {
    private PaymentStrategy strategy;

    public PaymentContext(String paymentMethod) throws WebApplicationException {
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
                Response r = Response.status(Response.Status.BAD_REQUEST).entity("Invalid payment method: " + paymentMethod).build();
                throw new WebApplicationException(r);
        }
    }

    public String process(PaymentRequestDTO paymentRequestDTO) {
        return strategy.processPayment(paymentRequestDTO);
    }
}