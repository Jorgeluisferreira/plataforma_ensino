package grupo1.aps2.controller;

import java.io.IOException;

import org.eclipse.microprofile.jwt.JsonWebToken;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import grupo1.aps2.dto.DTOUsuario.UsuarioDTO;
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

        try {
            // Parse do token
            SignedJWT signedJWT = SignedJWT.parse(jwtToken);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            Long id = Long.parseLong( (String) claims.getClaim("id"));
            String nome = (String) claims.getClaim("nome");
            String role = (String) claims.getClaim("roles");
    
            UsuarioDTO usuarioDTO = new UsuarioDTO(id, nome, role);

            // aqui você pode armazenar as infos no contexto da requisição se quiser
            requestContext.setProperty("usuario", usuarioDTO);

        } catch (Exception e) {
            ExceptionUtil.throwException(401, "JWT inválido: " + e.getMessage());
        }
    }
}
