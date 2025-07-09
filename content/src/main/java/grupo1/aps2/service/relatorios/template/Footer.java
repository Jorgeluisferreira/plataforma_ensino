package grupo1.aps2.service.relatorios.template;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Footer {
    private String expirationDate;
    private String rodape;

    public Footer() {
        this.rodape = "";
        this.expirationDate = "";
    }
}
