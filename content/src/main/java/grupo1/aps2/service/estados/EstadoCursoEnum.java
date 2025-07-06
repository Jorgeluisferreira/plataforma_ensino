package grupo1.aps2.service.estados;

public enum EstadoCursoEnum {
    DISPONIVEL,
    EM_ANDAMENTO,
    ENCERRADO;

    public static EstadoCurso toEstadoCurso(EstadoCursoEnum estadoEnum) {
        return switch (estadoEnum) {
            case DISPONIVEL -> new CursoDisponivel();
            case EM_ANDAMENTO -> new CursoEmAndamento();
            case ENCERRADO -> new CursoEncerrado();
        };
    }

    public EstadoCurso toEstadoCurso() {
        return toEstadoCurso(this);
    }
}
