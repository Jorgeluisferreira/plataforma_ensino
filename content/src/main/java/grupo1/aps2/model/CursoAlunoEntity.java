package grupo1.aps2.model;

import grupo1.aps2.service.estados.EstadoCursoEnum;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class CursoAlunoEntity extends PanacheEntityBase {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;

    @NotNull
    @ManyToOne
    private CursoEntity curso;

    @NotNull
    private Long usuarioId;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoCursoEnum status;

}

