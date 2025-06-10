package grupo1.aps2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class UsuarioEntity {

    @Id
    private Long id;
    private String nome;
    private String email;
    private String senha;

}
