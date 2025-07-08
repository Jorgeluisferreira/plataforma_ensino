package grupo1.aps2.service.estados;

import grupo1.aps2.dto.DTOCursoAluno.CursoAlunoBodyItem;
import grupo1.aps2.dto.DTOUsuario.UsuarioDTO;
import grupo1.aps2.service.relatorios.DocumentoTemplate;

public interface EstadoCurso {

    void enviarAtividade();
    DocumentoTemplate receberCertificado();
    EstadoCurso concluirEtapa();
    EstadoCursoEnum toEnum();
}
