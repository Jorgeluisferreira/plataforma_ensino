package grupo1.aps2.enums;

public enum PaymentStatus {
    PENDING("Pendente"),
    COMPLETED("Concluído"),
    FAILED("Falhou"),
    REFUNDED("Reembolsado");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static String getStatusFromDescription(String description) {
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.getDescription().equalsIgnoreCase(description)) {
                return status.name();
            }
        }
        throw new IllegalArgumentException("Status não encontrado: " + description);
    }

    public static boolean isRefunded(PaymentStatus status) {
        return status == REFUNDED;
    }
}
