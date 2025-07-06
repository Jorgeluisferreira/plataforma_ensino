package grupo1.aps2.service.relatorios;

import grupo1.aps2.dto.DTOUsuario;
import grupo1.aps2.dto.DTOUsuario.UsuarioDTO;
import grupo1.aps2.model.CursoAlunoEntity;

import java.util.List;

public class EstadoCursoDocumento implements Documento{

    UsuarioDTO usuario;
    CursoAlunoEntity curso;

    public EstadoCursoDocumento(DTOUsuario.UsuarioDTO usuario, CursoAlunoEntity curso) {
        this.usuario = usuario;
        this.curso = curso;

    }

    @Override
    public String getTitulo() {
        return "Atestado de Estado de Matrícula do Curso";
    }

    @Override
    public UsuarioDTO getUsuario() {
        return this.usuario;
    }

    @Override
    public List<CursoAlunoEntity> getCursos() {
        return List.of(curso);
    }

    @Override
    public String getTituloSessaoCursos() {
        return "Estágio Atual do Curso";
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
