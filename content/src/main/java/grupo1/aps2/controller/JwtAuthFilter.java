package grupo1.aps2.controller;

import grupo1.aps2.dto.DTOUsuario.UsuarioDTO;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthFilter implements ContainerRequestFilter {

    @Inject
    JsonWebToken jwt;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        if (jwt == null || !jwt.getClaimNames().iterator().hasNext()) {
                // Token inválido ou ausente
                requestContext.abortWith(
                        jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.UNAUTHORIZED).build()
                );
                return;
        }

        // Exemplo: extrair claims e criar DTO
        Long id = jwt.getClaim("id");
        String nome = jwt.getClaim("nome");
        String role = jwt.getClaim("role");
        // senha não deve estar no JWT por segurança

        UsuarioDTO usuarioDTO = new UsuarioDTO(id, nome, role);

        // Aqui você pode salvar o usuarioDTO em algum contexto, se necessário
        requestContext.setProperty("usuario", usuarioDTO);
    }
}
