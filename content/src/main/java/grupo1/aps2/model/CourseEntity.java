package grupo1.aps2.model;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class CourseEntity {
    
    @Id
    private long id;
    
    @NotBlank
    private String nome;
    
    @Valid
    private Collection<String> categorias;

    private String descricao;

}

