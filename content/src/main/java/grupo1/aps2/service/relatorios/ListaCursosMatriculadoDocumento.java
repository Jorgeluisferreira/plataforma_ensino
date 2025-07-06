package grupo1.aps2.service.relatorios;

import grupo1.aps2.dto.DTOUsuario;
import grupo1.aps2.model.CursoAlunoEntity;

import java.util.List;

public class ListaCursosMatriculadoDocumento implements Documento {

    DTOUsuario.UsuarioDTO usuario;
    List<CursoAlunoEntity> cursos;

    public ListaCursosMatriculadoDocumento(DTOUsuario.UsuarioDTO usuario, List<CursoAlunoEntity> cursos) {
        this.usuario = usuario;
        this.cursos = cursos;
    }

    @Override
    public String getTitulo() {
        return "Lista de Cursos Matriculado";
    }

    @Override
    public DTOUsuario.UsuarioDTO getUsuario() {
        return this.usuario;
    }

    @Override
    public List<CursoAlunoEntity> getCursos() {
        return cursos;
    }

    @Override
    public String getTituloSessaoCursos() {
        return "Lista de Cursos Matriculados";
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
