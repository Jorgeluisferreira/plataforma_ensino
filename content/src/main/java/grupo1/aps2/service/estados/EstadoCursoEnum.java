package grupo1.aps2.service.estados;

public enum EstadoCursoEnum {
    NAO_MATRICULADO,
    EM_ANDAMENTO,
    CONCLUIDO;

    public static EstadoCurso toEstadoCurso(EstadoCursoEnum estadoEnum) {
        return switch (estadoEnum) {
            case NAO_MATRICULADO -> new CursoNaoMatriculado();
            case EM_ANDAMENTO -> new CursoEmAndamento();
            case CONCLUIDO -> new CursoConcluido();
        };
    }

    public EstadoCurso toEstadoCurso() {
        return toEstadoCurso(this);
    }
}
