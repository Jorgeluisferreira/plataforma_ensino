package grupo1.aps2.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ContentEntity extends PanacheEntity {
    
    @Id
    private long id;
    private String nome;
    private String descricao;
    
}

