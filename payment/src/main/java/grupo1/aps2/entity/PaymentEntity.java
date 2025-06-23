package grupo1.aps2.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class PaymentEntity extends PanacheEntity {
    private Long userId;
    private String paymentMethod;
    private BigDecimal amount;
    private String status;
}
