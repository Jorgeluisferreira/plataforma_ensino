package grupo1.aps2.controller;

import grupo1.aps2.dto.DTOUsuario;
import grupo1.aps2.exceptions.ExceptionUtil;
import grupo1.aps2.exceptions.MensagemErro;
import grupo1.aps2.model.UsuarioEntity;
import grupo1.aps2.security.Roles;
import grupo1.aps2.service.UsuarioService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.logging.Log;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static grupo1.aps2.dto.DTOUsuario.CadastroUsuarioDTO;

@ApplicationScoped
@Path("users")
public class UsuarioController {

    UsuarioService usuarioService;

    @Inject
    UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GET
    @Path("/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<Long> getUsuarioById(@PathParam("idUsuario") Long idUsuario) {
//        CadastroUsuarioDTO user = usuarioService.getUsuarioById(idUsuario);
        return RestResponse.ResponseBuilder.ok(idUsuario, MediaType.APPLICATION_JSON).build();
    }

    @GET
    public RestResponse<List<UsuarioEntity>> getUsuarios() {
        List<UsuarioEntity> usuarios = UsuarioEntity.listAll();
        return ResponseBuilder.ok(usuarios, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/cadastrarAluno")
    @PermitAll
    public RestResponse<String> cadastrarAluno(CadastroUsuarioDTO cadastro, @HeaderParam("Authorization") String authHeader) {
        if (cadastro == null || cadastro.getNome() == null || cadastro.getNome().isBlank()) {
            ExceptionUtil.throwException(400, "É necessário informar o nome do usuário.");
        }
        usuarioService.cadastrarUsuario(cadastro, Roles.ALUNO, parseAuthorizationHeader(authHeader));
        return ResponseBuilder.ok("Usuario cadastrado como aluno com sucesso").build();
    }

    @POST
    @Path("/cadastrarProfessor")
    @RolesAllowed({ "Admin" })
    public RestResponse<String> cadastrarProfessor(@Valid CadastroUsuarioDTO cadastro) {
        usuarioService.cadastrarOutroUsuario(cadastro, Roles.PROFESSOR);
        return ResponseBuilder.ok("Usuario cadastrado como professor com sucesso").build();
    }

    @POST
    @Path("/cadastrarAdmin")
    @RolesAllowed({ "Admin" })
    public RestResponse<String> cadastrarAdmin(@Valid CadastroUsuarioDTO cadastro) {
        usuarioService.cadastrarOutroUsuario(cadastro, Roles.ADMIN);
        return ResponseBuilder.ok("Usuario cadastrado como administrador com sucesso").build();
    }

    @POST
    @Path("/cadastrarAdminBypass")
    @PermitAll
    public RestResponse<String> cadastrarAdminBypass(CadastroUsuarioDTO cadastro, @HeaderParam("Authorization") String authHeader) {
        DTOUsuario.AuthCredentials credenciais = parseAuthorizationHeader(authHeader);
        usuarioService.cadastrarAdminBypass(cadastro, credenciais);
        return ResponseBuilder.ok("Usuario cadastrado como administrador com sucesso").build();
    }

    @POST
    @Path("/login")
    public RestResponse<String> login(@HeaderParam("Authorization") String authHeader) {
        String token = usuarioService.logarUsuario(parseAuthorizationHeader(authHeader));
        NewCookie cookie = new NewCookie("jwt", token, "/", null, null, 3600, true, true);
        return ResponseBuilder
                .ok(usuarioService.logarUsuario(parseAuthorizationHeader(authHeader)))
                .header("Set-Cookie", cookie)
                .build();
    }

    private DTOUsuario.AuthCredentials parseAuthorizationHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            ExceptionUtil.throwException(401, MensagemErro.INVALID_AUTH_HEADER);
        }
        String base64Credentials = authHeader.substring("Basic ".length());
        String[] values = new String[2];
        try {
            values = new String(
                    Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8)
                    .split(":", 2);

        } catch (IllegalArgumentException e) {
            Log.error("Erro ao decodificar o cabeçalho de autorização: " + e.getMessage());
            ExceptionUtil.throwException(400, MensagemErro.INVALID_AUTH_HEADER);
        }
        return new DTOUsuario.AuthCredentials(values[0], values[1]);
    }
}
