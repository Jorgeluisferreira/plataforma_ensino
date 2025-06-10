package grupo1.aps2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class DTOUsuario {
    private DTOUsuario() {}

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class UsuarioDTO {
        private Long id;
        private String nome;
    }

}
