package grupo1.aps2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ContentEntity {
    
    @Id
    private long id;
    private String nome;
    private String descricao;

}

