package grupo1.aps2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class AulaEntity {
    
    @Id
    private long id;
    
    @NotBlank
    private String nome;

    private String descricao;

    @NotBlank
    private String contentURL;

}

