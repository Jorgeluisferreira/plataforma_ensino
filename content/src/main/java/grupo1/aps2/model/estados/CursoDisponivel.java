package grupo1.aps2.model.estados;

public class CursoDisponivel implements EstadoCurso{
    public void fazerMatricula(){

    }

    public void cancelarMatricula(){

    }

    public void enviarAtividade(){

    }

    public void receberCertificado(){

    }

    public EstadoCurso concluirEtapa(){
        return new CursoEmAndamento();   
    }
}
