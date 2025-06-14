package grupo1.aps2.service;

import grupo1.aps2.dto.DTOUsuario;
import grupo1.aps2.exceptions.UnauthorizedUserException;
import grupo1.aps2.mapper.UsuarioMapper;
import grupo1.aps2.model.UsuarioEntity;
import grupo1.aps2.security.Roles;
import grupo1.aps2.security.jwt.GenerateJwtToken;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.identity.IdentityProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;

import java.nio.charset.StandardCharsets;
import java.util.*;

@ApplicationScoped
public class UsuarioService {

    @Inject
    UsuarioMapper usuarioMapper;

    @Inject
    GenerateJwtToken jwtGenerator;

    @Transactional
    public void cadastrarUsuario(DTOUsuario.CadastroUsuarioDTO usuarioDTO, Roles role, String authHeader) {

        DTOUsuario.AuthCredentials auth = parseAuthorizationHeader(authHeader);
        usuarioDTO.setEmail(auth.email());
        usuarioDTO.setSenha(auth.senha());

        UsuarioEntity usuarioEntity = usuarioMapper.fromCadastro(usuarioDTO);
        usuarioEntity.setRoles(new HashSet<>(Set.of(role.getRole())));
        usuarioEntity.persist();
    }

    public String logarUsuario(String authHeader) {

        DTOUsuario.AuthCredentials auth = parseAuthorizationHeader(authHeader);

        System.out.println("Cadastro de administrador: " + auth);

        UsuarioEntity usuario = UsuarioEntity.find("email", auth.email()).firstResult();

        validarLoginUsuario(usuario, auth);

        return jwtGenerator.generateUserToken(usuario);
    }

    private DTOUsuario.AuthCredentials parseAuthorizationHeader(String authHeader) {
        String base64Credentials = authHeader.substring("Basic ".length());
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
        String[] values = credentials.split(":", 2);
        return new DTOUsuario.AuthCredentials(values[0], values[1]);
    }

    private void validarLoginUsuario(UsuarioEntity entity, DTOUsuario.AuthCredentials credenciais
        ) {
        if (entity == null || !BcryptUtil.matches(entity.getSenha(), credenciais.senha())) {
            throw new BadRequestException("Invalid email or password.");
        }
    }
}