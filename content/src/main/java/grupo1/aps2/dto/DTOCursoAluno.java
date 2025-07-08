package grupo1.aps2.dto;

import grupo1.aps2.dto.DTOUsuario.UsuarioDTO;
import grupo1.aps2.service.estados.EstadoCursoEnum;
import grupo1.aps2.service.relatorios.template.BodyItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class DTOCursoAluno {
    private DTOCursoAluno(){}

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class CursoAlunoDTO {
        protected Long id;
        protected UsuarioDTO usuario;
        protected DTOCurso.CursoDTO curso;
        protected EstadoCursoEnum status;
        protected LocalDateTime dataHoraConclusao;
    }

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class CursoAlunoBodyItem implements BodyItem {

        protected Long id;
        protected UsuarioDTO usuario;
        protected DTOCurso.CursoDTO curso;
        protected EstadoCursoEnum status;
        protected LocalDateTime dataHoraConclusao;

        @Override
        public String getNome() {
            return getCurso().getNome();
        }

        @Override
        public String getDescricao() {
            return getCurso().getDescricao();
        }

        @Override
        public String getEstadoItem() {
            return status.toString();
        }
    }
}
