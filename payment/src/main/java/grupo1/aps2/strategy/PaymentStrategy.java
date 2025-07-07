package grupo1.aps2.strategy;

import grupo1.aps2.dto.DTOPayment.*;

public interface PaymentStrategy {
    String processPayment(PaymentRequestDTO paymentRequestDTO);
}