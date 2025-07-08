package grupo1.aps2.service.relatorios;

import grupo1.aps2.dto.DTOCursoAluno.CursoAlunoBodyItem;
import grupo1.aps2.dto.DTOUsuario;
import grupo1.aps2.dto.DTOUsuario.UsuarioDTO;
import grupo1.aps2.service.relatorios.template.BodyItem;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class EstadoCursoDocumentoTemplate extends DocumentoTemplate {

    UsuarioDTO usuario;
    CursoAlunoBodyItem curso;

    public EstadoCursoDocumentoTemplate(DTOUsuario.UsuarioDTO usuario, CursoAlunoBodyItem curso) {
        this.usuario = usuario;
        this.curso = curso;
    }

    @Override
    public UsuarioDTO getUsuario() {
        return this.usuario;
    }

    @Override
    protected String getDocumentTitle() {
        return "Atestado de Matrícula do Curso";
    }

    @Override
    protected LocalDateTime getDocumentGenerationTimestamp() {
        return LocalDateTime.now();
    }

    @Override
    protected LocalDateTime getDocumentOriginalTimestamp() {
        return LocalDateTime.now();
    }

    @Override
    protected LocalDateTime getDocumentExpirationTimestamp() {
        return LocalDateTime.now().plusMonths(3);
    }

    @Override
    protected List<BodyItem> getBodyContent() {
        return List.of(curso);
    }

    @Override
    public String getBodyTitle() {
        return "Estágio Atual do Curso";
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
        this.curso = (CursoAlunoBodyItem) bodyContent.getFirst();
    }
}
