package grupo1.aps2.enums;

public enum PaymentMethods {

    CREDIT_CARD("Credito"),
    DEBIT_CARD("Debito"),
    PIX("Pix");

    private final String description;

    PaymentMethods(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static boolean isValidMethod(String method) {
        for (PaymentMethods paymentMethod : values()) {
            if (paymentMethod.name().equalsIgnoreCase(method)) {
                return true;
            }
        }
        return false;
    }
}
