package grupo1.aps2.strategy;

import grupo1.aps2.dto.DTOPayment.*;

public class PixPayment implements PaymentStrategy {
    @Override
    public String processPayment(PaymentRequestDTO paymentRequestDTO) {
        return "Pix payment processed for " + paymentRequestDTO.getUserName();
    }
}
