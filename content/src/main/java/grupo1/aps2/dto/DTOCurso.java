package grupo1.aps2.dto;

import java.math.BigDecimal;
import java.util.Collection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class DTOCurso {
    private DTOCurso(){}

    @Getter@Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class CadastroCursoDTO {

        @NotBlank
        private String nome;
        private Collection<String> categorias;
        @NotBlank
        private String descricao;
        @NotNull
        private BigDecimal preco;
    }

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class CursoDTO {
        private Long id;
        private String nome;
        private Collection<String> categorias;
        private String descricao;
        private BigDecimal preco;
    }

}
