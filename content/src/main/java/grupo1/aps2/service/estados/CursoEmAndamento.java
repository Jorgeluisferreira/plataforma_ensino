package grupo1.aps2.service.estados;

public class CursoEmAndamento implements EstadoCurso{
    public void fazerMatricula(){

    }

    public void cancelarMatricula(){

    }

    public void enviarAtividade(){
        
    }

    public void receberCertificado(){

    }

    public EstadoCurso concluirEtapa(){
        return new CursoEncerrado();   
    }

    @Override
    public EstadoCursoEnum toEnum() {
        return EstadoCursoEnum.EM_ANDAMENTO;
    }
}
