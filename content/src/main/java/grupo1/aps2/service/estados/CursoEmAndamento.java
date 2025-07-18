package grupo1.aps2.service.estados;

import grupo1.aps2.dto.DTOCursoAluno;
import grupo1.aps2.dto.DTOUsuario;
import grupo1.aps2.service.relatorios.DocumentoTemplate;
import grupo1.aps2.service.relatorios.EstadoCursoDocumentoTemplate;

public class CursoEmAndamento implements EstadoCurso{
    public void enviarAtividade(){
        
    }

    public DocumentoTemplate receberCertificado(){
        return new EstadoCursoDocumentoTemplate();
    }

    public EstadoCurso concluirEtapa(){
        return new CursoConcluido();
    }

    @Override
    public EstadoCursoEnum toEnum() {
        return EstadoCursoEnum.EM_ANDAMENTO;
    }
}
