package grupo1.aps2.model.estados;

public interface EstadoCurso {
    void fazerMatricula();
    void cancelarMatricula();
    void enviarAtividade();
    void receberCertificado();
    EstadoCurso concluirEtapa();

    EstadoCursoEnum toEnum();
}
