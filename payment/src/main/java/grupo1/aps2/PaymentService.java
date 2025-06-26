package grupo1.aps2;

import grupo1.aps2.dto.DTOPayment.PaymentConfirmationDTO;
import grupo1.aps2.dto.DTOPayment.PaymentRequestDTO;
import grupo1.aps2.dto.DTOPayment.RefundConfirmationDTO;
import grupo1.aps2.dto.DTOPayment.RefundRequestDTO;
import grupo1.aps2.entity.PaymentEntity;
import grupo1.aps2.enums.PaymentStatus;
import grupo1.aps2.mapper.PaymentMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PaymentService {

    @Inject
    PaymentMapper paymentMapper;

    public PaymentConfirmationDTO processPayment(PaymentRequestDTO request) {

        PaymentEntity entity = paymentMapper.fromPaymentRequest(request);
        entity.persistAndFlush();
        return paymentMapper.toPaymentConfirmation(entity);

    }

    public RefundConfirmationDTO refundPayment(RefundRequestDTO request) {

        PaymentEntity entity = PaymentEntity.findById(request.getPaymentId());
        entity.setStatus(PaymentStatus.REFUNDED.getDescription());
        entity.setRefunded(true);
        entity.persistAndFlush();


        return paymentMapper.toRefundConfirmation(entity);

    }
}