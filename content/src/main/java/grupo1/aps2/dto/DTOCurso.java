package grupo1.aps2.dto;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class DTOCurso {
    private DTOCurso(){}

    @Getter@Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class CadastroCursoDTO {
        private Long id;
        private String nome;
        private Collection<String> categorias;
        private String descricao;
    }

}
