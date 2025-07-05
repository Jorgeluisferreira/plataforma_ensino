package grupo1.aps2.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class AulaEntity extends PanacheEntityBase {
    
    @Id
    private Long id;

    @NotNull
    @ManyToOne
    private CursoEntity curso;
    
    @NotBlank
    private String nome;

    private String descricao;

    @NotBlank
    private String contentURL;

}

