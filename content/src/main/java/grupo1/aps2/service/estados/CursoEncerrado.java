package grupo1.aps2.service.estados;

public class CursoEncerrado implements EstadoCurso{
    public void fazerMatricula(){

    }

    public void cancelarMatricula(){

    }

    public void enviarAtividade(){
        
    }

    public void receberCertificado(){

    }

    public EstadoCurso concluirEtapa(){
        return this;   
    }

    @Override
    public EstadoCursoEnum toEnum() {
        return EstadoCursoEnum.ENCERRADO;
    }
}
