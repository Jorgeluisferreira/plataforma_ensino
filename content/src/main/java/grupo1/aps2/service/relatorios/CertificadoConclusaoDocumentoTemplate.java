package grupo1.aps2.service.relatorios;

import grupo1.aps2.dto.DTOCursoAluno;
import grupo1.aps2.dto.DTOCursoAluno.CursoAlunoBodyItem;
import grupo1.aps2.dto.DTOCursoAluno.CursoAlunoDTO;
import grupo1.aps2.dto.DTOUsuario.UsuarioDTO;
import grupo1.aps2.service.relatorios.template.BodyItem;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class CertificadoConclusaoDocumentoTemplate extends DocumentoTemplate{

    UsuarioDTO usuario;
    ConclusaoBodyItem curso;

    @Override
    public UsuarioDTO getUsuario() {
        return usuario;
    }

    @Override
    protected String getDocumentTitle() {
        return "Atestado de Conclusão de Curso";
    }

    @Override
    protected LocalDateTime getDocumentGenerationTimestamp() {
        return LocalDateTime.now();
    }

    @Override
    protected LocalDateTime getDocumentOriginalTimestamp() {
        return curso.cursoAluno().getDataHoraConclusao();
    }

    @Override
    protected LocalDateTime getDocumentExpirationTimestamp() {
        return curso.cursoAluno().getDataHoraConclusao().plusYears(2);
    }

    @Override
    protected List<BodyItem> getBodyContent() {
        return List.of(curso);
    }

    @Override
    protected String getBodyTitle() {
        return "";
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    @Override
    public void setBodyContent(List<BodyItem> bodyContent) {
        BodyItem item = bodyContent.getFirst();
        if (item instanceof CursoAlunoBodyItem cabi) {
            this.curso = new ConclusaoBodyItem(
                    new CursoAlunoDTO(
                            cabi.getId(),
                            cabi.getUsuario(),
                            cabi.getCurso(),
                            cabi.getStatus(),
                            cabi.getDataHoraConclusao()
                    ));

        } else if (item instanceof ConclusaoBodyItem cbi) {
            this.curso = cbi;
        }
    }

    public record ConclusaoBodyItem(CursoAlunoDTO cursoAluno) implements BodyItem {

        @Override
        public String getNome() {
            return "A Areas Academy ";
        }

        @Override
        public String getDescricao() {
            StringBuilder sb = new StringBuilder();
            sb.append("certifica que ")
                    .append(cursoAluno.getUsuario().getNome())
                    .append(" concluiu o curso de ")
                    .append(cursoAluno.getCurso().getNome())
                    .append(" na data ")
                    .append(cursoAluno.getDataHoraConclusao().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .append(" com sucesso.");
            return  sb.toString();
        }

        @Override
        public String getEstadoItem() {
            return "";
        }
    }
}
