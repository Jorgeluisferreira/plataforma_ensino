package grupo1.aps2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class DTOAula {
    private DTOAula(){}

    @Getter@Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class CadastroAulaDTO {
        private Long id;
        private Long curso_id;
        private String nome;
        private String descricao;
        private String contentURL;
    }

}
