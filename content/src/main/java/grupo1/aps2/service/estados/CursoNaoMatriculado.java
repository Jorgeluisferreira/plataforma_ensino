package grupo1.aps2.service.estados;

import grupo1.aps2.exceptions.ExceptionUtil;
import grupo1.aps2.service.relatorios.DocumentoTemplate;
import grupo1.aps2.service.relatorios.EstadoCursoDocumentoTemplate;

public class CursoNaoMatriculado implements EstadoCurso{
    public void enviarAtividade(){
        ExceptionUtil.throwException(400, "Curso ainda não iniciado, não é possível enviar atividades.");
    }

    public DocumentoTemplate receberCertificado(){
        return new EstadoCursoDocumentoTemplate();
    }

    public EstadoCurso concluirEtapa(){
        return new CursoEmAndamento();   
    }

    @Override
    public EstadoCursoEnum toEnum() {
        return EstadoCursoEnum.NAO_MATRICULADO;
    }
}
