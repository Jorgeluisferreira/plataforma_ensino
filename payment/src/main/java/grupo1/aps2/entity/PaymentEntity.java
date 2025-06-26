package grupo1.aps2.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PaymentEntity extends PanacheEntity {

    @NotNull
    private Long userId;

    @NotBlank
    private String paymentMethod;

    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String status;

    @NotNull
    private boolean isRefunded;
}
