package grupo1.aps2.strategy;

import grupo1.aps2.dto.DTOPayment.*;

public class PaymentContext {
    private PaymentStrategy strategy;

    public PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public String process(PaymentRequestDTO paymentRequestDTO) {
        return strategy.processPayment(paymentRequestDTO);
    }
}