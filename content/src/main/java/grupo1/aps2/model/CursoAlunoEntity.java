package grupo1.aps2.model;

import grupo1.aps2.model.estados.EstadoCurso;
import grupo1.aps2.model.estados.EstadoCursoEnum;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class CursoAlunoEntity extends PanacheEntityBase {
   
    @Id
    private Long id;

    @NotBlank
    @ManyToOne
    private CursoEntity curso;

    @NotBlank
    private Long usuarioId;
    
    @NotBlank
    @Enumerated(EnumType.STRING)
    private EstadoCursoEnum status;

}

