package grupo1.aps2.service.relatorios;

import grupo1.aps2.dto.DTOCursoAluno.CursoAlunoBodyItem;
import grupo1.aps2.dto.DTOUsuario;
import grupo1.aps2.service.relatorios.template.BodyItem;

import java.time.LocalDateTime;
import java.util.List;

public class ListaCursosMatriculadoDocumentoTemplate extends DocumentoTemplate {

    DTOUsuario.UsuarioDTO usuario;
    List<CursoAlunoBodyItem> cursos;

    public ListaCursosMatriculadoDocumentoTemplate(DTOUsuario.UsuarioDTO usuario, List<CursoAlunoBodyItem> cursos) {
        this.usuario = usuario;
        this.cursos = cursos;
    }

    @Override
    public DTOUsuario.UsuarioDTO getUsuario() {
        return this.usuario;
    }

    @Override
    protected String getDocumentTitle() {
        return "Lista de Cursos Matriculado";
    }

    @Override
    protected LocalDateTime getDocumentTimestamp() {
        return LocalDateTime.now();
    }

    @Override
    protected List<BodyItem> getBodyContent() {
        return List.of(cursos.toArray(new CursoAlunoBodyItem[0]));
    }

    @Override
    protected String getBodyTitle() {
        return "Lista de Cursos Matriculado";
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
