package grupo1.aps2.model;

import java.util.Collection;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class CursoEntity extends PanacheEntityBase {
    
    @Id
    private Long id;

    @NotBlank
    private String nome;
    
    @Valid
    @ElementCollection
    private Collection<String> categorias;
    
    private String descricao;

}

