package grupo1.aps2.dto;

import grupo1.aps2.model.estados.EstadoCurso;
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
        private Long usuarioId;
        private Long cursoId;
        private EstadoCurso status;
    }

}
