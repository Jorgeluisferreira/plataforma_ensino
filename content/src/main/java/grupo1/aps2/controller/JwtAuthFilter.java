package grupo1.aps2.controller;

import java.io.IOException;

import org.eclipse.microprofile.jwt.JsonWebToken;

import grupo1.aps2.exceptions.ExceptionUtil;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthFilter implements ContainerRequestFilter {

    @Inject
    JsonWebToken jwt;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        Cookie jwtCookie = requestContext.getCookies().get("jwt");

        if (jwtCookie == null || jwtCookie.getValue() == null || jwtCookie.getValue().isEmpty()) {
            ExceptionUtil.throwException(401, "JWT não encontrado no cookie");
            return;
        }

        String jwtToken = jwtCookie.getValue();
        System.out.println("Token JWT recebido via cookie: " + jwtToken);
        // if (jwt == null || jwt.getIssuer() == null) {
        //     ExceptionUtil.throwException(401, "JWT não encontrado ou inválido");
        // }
        // Long id = Long.parseLong(jwt.getClaim("id"));
        // String nome = jwt.getClaim("nome");
        // String role = jwt.getClaim("roles");
        // UsuarioDTO usuarioDTO = new UsuarioDTO(id, nome, role);

        // // Aqui você pode salvar o usuarioDTO em algum contexto, se necessário
        // requestContext.setProperty("usuario", usuarioDTO);
    }
}
