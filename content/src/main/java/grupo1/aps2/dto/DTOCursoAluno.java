package grupo1.aps2.dto;

import grupo1.aps2.dto.DTOUsuario.UsuarioDTO;
import grupo1.aps2.service.estados.EstadoCursoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class DTOCursoAluno {
    private DTOCursoAluno(){}

    @Getter@Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class CursoAlunoDTO {
        private Long id;
        private UsuarioDTO usuario;
        private DTOCurso.CursoDTO curso;
        private EstadoCursoEnum status;
    }
}
