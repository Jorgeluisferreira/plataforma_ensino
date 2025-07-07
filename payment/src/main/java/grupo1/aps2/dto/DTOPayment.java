package grupo1.aps2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

public class DTOPayment {

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class UserInfo {
        private Long userId;
        private String nome;
        private String email;
    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class PaymentConfirmationDTO {
        private String userEmail;
        private String paymentMethod;
        private String status;
    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class RefundConfirmationDTO {
        private String userEmail;
        private String paymentMethod;
        private BigDecimal amount;
        private String status;
    }

//    @Getter @Setter
//    @AllArgsConstructor @NoArgsConstructor
//    public static class PaymentRequestDTO {
//        private UserInfo userInfo;
//
//        private String paymentMethod;
//        private BigDecimal amount;
//        private String status;
//    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class PaymentRequestDTO {
        private Long userId;
        private String userName;
        private String userEmail;
        private String paymentMethod;
        private BigDecimal amount;
    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class RefundRequestDTO {
        private Long paymentId;
        private UserInfo userInfo;
    }
}
