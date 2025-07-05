package grupo1.aps2.model;

import grupo1.aps2.model.estados.EstadoCurso;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class CursoAlunoEntity {
   
    @Id
    private Long id;

    @NotBlank
    private Long cursoId;

    @NotBlank
    private Long usuarioId;
    
    @NotBlank
    private EstadoCurso status;

}

