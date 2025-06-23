package grupo1.aps2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class DTOPayment {

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class UserInfoDTO {

    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class PaymentInfoDTO {
    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class PaymentConfirmationDTO {

    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class RefundConfirmationDTO {

    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class PaymentRequestDTO {
        private UserInfoDTO userInfo;
        private PaymentInfoDTO paymentInfo;
    }

    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class RefundRequestDTO {

    }
}
