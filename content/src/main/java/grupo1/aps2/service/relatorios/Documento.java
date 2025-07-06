package grupo1.aps2.service.relatorios;

import grupo1.aps2.dto.DTOUsuario.UsuarioDTO;
import grupo1.aps2.model.CursoAlunoEntity;

import java.util.List;

public interface Documento {

    public String getTitulo();
    public UsuarioDTO getUsuario();
    public List<CursoAlunoEntity> getCursos();
    public String getTituloSessaoCursos();
    public abstract boolean isValid();
}
