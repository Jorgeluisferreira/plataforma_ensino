package grupo1.aps2.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.quarkus.resteasy.reactive.jackson.CustomDeserialization;
import io.quarkus.security.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class DTOUsuario {
    private DTOUsuario() {}

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class CadastroUsuarioDTO {

        @Size(min = 2, max = 96, message = "O nome deve ter entre 2 e 50 caracteres.")
        private String nome;

        @Email(message = "O email deve ser válido.")
        private String email;

        @Size(min = 6, max = 64, message = "A senha deve ter entre 6 e 64 caracteres.")
        private String senha;

    }

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class LoginUsuarioDTO {

        @Email(message = "O email deve ser válido.")
        @NotBlank
        private String email;

        @Size(min = 6, max = 64, message = "A senha deve ter entre 6 e 64 caracteres.")
        @NotBlank
        private String senha;
    }

    public static record AuthCredentials(
            @Email(message = "O email deve ser válido.")
            @NotBlank(message = "O email não pode ser nulo ou vazio.")
            String email,

            @Size(min = 6, max = 64, message = "A senha deve ter entre 6 e 64 caracteres.")
            @NotBlank(message = "A senha não pode ser nula ou vazia.")
            String senha) {
    }
}
