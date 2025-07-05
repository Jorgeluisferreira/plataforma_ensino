package grupo1.aps2.model;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.security.jpa.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.wildfly.security.password.interfaces.BCryptPassword;

import java.util.Collection;
import java.util.List;

@Entity
@UserDefinition
@Getter @Setter
public class UsuarioEntity extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "matricula_seq", sequenceName = "matricula_seq", allocationSize = 1,initialValue = 100000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "matricula_seq")
    private Long id;

    @NotBlank(message = "O nome não pode ser nulo ou vazio.")
    private String nome;

    @NotBlank(message = "O email não pode ser nulo ou vazio.")
    @Email(message = "O email deve ser válido.")
    @Column(unique = true)
    @Username
    private String email;

    @NotBlank(message = "A senha não pode ser nula ou vazia.")
    @Size(min = 6, max = 64, message = "A senha deve ter entre 6 e 64 caracteres.")
    @Password
    private String senha;

    @NotEmpty
    @Roles
    public String role;
}
