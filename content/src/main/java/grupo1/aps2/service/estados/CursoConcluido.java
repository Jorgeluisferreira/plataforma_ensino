package grupo1.aps2.service.estados;

import grupo1.aps2.service.relatorios.CertificadoConclusaoDocumentoTemplate;
import grupo1.aps2.service.relatorios.DocumentoTemplate;

public class CursoConcluido implements EstadoCurso{
    public void enviarAtividade(){
        
    }

    public DocumentoTemplate receberCertificado(){
        return new CertificadoConclusaoDocumentoTemplate();
    }

    public EstadoCurso concluirEtapa(){
        return this;   
    }

    @Override
    public EstadoCursoEnum toEnum() {
        return EstadoCursoEnum.CONCLUIDO;
    }
}
