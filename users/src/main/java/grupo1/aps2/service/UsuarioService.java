package grupo1.aps2.service;

import grupo1.aps2.dto.DTOUsuario;
import grupo1.aps2.exceptions.ExceptionUtil;
import grupo1.aps2.mapper.UsuarioMapper;
import grupo1.aps2.model.UsuarioEntity;
import grupo1.aps2.security.Roles;
import grupo1.aps2.security.jwt.GenerateJwtToken;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.nio.charset.StandardCharsets;
import java.util.*;

@ApplicationScoped
public class UsuarioService {

    @ConfigProperty(name = "app.admin.bypass")
    private String adminBypass;

    @Inject
    UsuarioMapper usuarioMapper;

    @Inject
    GenerateJwtToken jwtGenerator;

    @Transactional
    public void cadastrarOutroUsuario(DTOUsuario.CadastroUsuarioDTO usuarioDTO, Roles role) {

        UsuarioEntity usuarioEntity = usuarioMapper.fromCadastro(usuarioDTO);
        usuarioEntity.setRoles(new HashSet<>(Set.of(role.getRole())));

        usuarioEntity.persist();
    }

    @Transactional
    public void cadastrarUsuario(DTOUsuario.CadastroUsuarioDTO usuarioDTO, Roles role, @Valid DTOUsuario.AuthCredentials auth) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        usuarioEntity.setNome(usuarioDTO.getNome());
        usuarioEntity.setEmail(auth.email());
        usuarioEntity.setSenha(BcryptUtil.bcryptHash(auth.senha()));
        usuarioEntity.setRoles(new HashSet<>(Set.of(role.getRole())));

        usuarioEntity.persist();
    }

    public void cadastrarAdminBypass(DTOUsuario.CadastroUsuarioDTO usuarioDTO, DTOUsuario.AuthCredentials auth) {

        if(!BcryptUtil.matches(auth.senha(), adminBypass)) {
            ExceptionUtil.throwException(400, "Senha de administrador inválida");
        }
        cadastrarOutroUsuario(usuarioDTO, Roles.ADMIN);
    }

    public String logarUsuario(@Valid DTOUsuario.AuthCredentials auth) {

        UsuarioEntity usuario = UsuarioEntity.find("email", auth.email()).firstResult();

        validarLoginUsuario(usuario, auth);

        return jwtGenerator.generateUserToken(usuario);
    }

    private void validarLoginUsuario(UsuarioEntity entity, DTOUsuario.AuthCredentials credenciais
        ) {
        if (entity == null || !BcryptUtil.matches(credenciais.senha(), entity.getSenha())) {
            ExceptionUtil.throwException(400, "Usuário ou senha inválidos");
        }
    }
}