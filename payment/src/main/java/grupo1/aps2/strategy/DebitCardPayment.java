package grupo1.aps2.strategy;

import grupo1.aps2.dto.DTOPayment.*;

public class DebitCardPayment implements PaymentStrategy {

    @Override
    public String processPayment(PaymentRequestDTO paymentRequestDTO) {
        return "success";
    }
}