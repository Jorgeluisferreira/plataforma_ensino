package grupo1.aps2.model;

import java.math.BigDecimal;
import java.util.Collection;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class CursoEntity extends PanacheEntityBase {

    @GeneratedValue
    @Id
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    private BigDecimal preco;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Collection<String> categorias;
    
    private String descricao;

}

