package grupo1.aps2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class DTOContent {
    private DTOContent(){}

    @Getter@Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class ContentDTO {
        private Long id;
        private String nome;
    }
}
