package grupo1.aps2.mapper;

import grupo1.aps2.dto.DTOPayment.*;
import grupo1.aps2.entity.PaymentEntity;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PaymentMapper {
    @Inject
    JWTParser jwtParser;

    public abstract PaymentEntity fromPaymentRequest(PaymentRequestDTO source);
    public abstract PaymentEntity fromRefundRequest(RefundRequestDTO source);

    public abstract PaymentConfirmationDTO toPaymentConfirmation(PaymentEntity source);
    public abstract RefundConfirmationDTO toRefundConfirmation(PaymentEntity source);

    public UserInfo fromJwt(String jwt) {
        JsonWebToken json = null;

        try {
            json = jwtParser.parse(jwt);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
        if (json == null) {
            throw new RuntimeException("JWT token is null");
        }

        Long userId = json.getClaim("userId");
        String nome = json.getClaim("nome");
        String email = json.getClaim("email");

        return new UserInfo(userId, nome, email);
    }
}
