package grupo1.aps2.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Collection;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

import grupo1.aps2.dto.DTOUsuario;
import grupo1.aps2.dto.DTOUsuario.CadastroUsuarioDTO;
import grupo1.aps2.dto.DTOUsuario.UsuarioDTO;
import grupo1.aps2.exceptions.ExceptionUtil;
import grupo1.aps2.exceptions.MensagemErro;
import grupo1.aps2.model.UsuarioEntity;
import grupo1.aps2.security.Roles;
import grupo1.aps2.service.UsuarioService;
import io.quarkus.logging.Log;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import io.smallrye.jwt.auth.principal.JWTParser;
import jakarta.ws.rs.core.NewCookie;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("users")
public class UsuarioController {

    UsuarioService usuarioService;

    @Inject
    UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @Inject
    JWTParser jwtParser;

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
    @Path("/UsuarioInfos")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<UsuarioDTO> infosUsuario(@CookieParam("jwt") String tokenCookie){
        UsuarioDTO dados = null;
        try {
            // Decodifica e valida o JWT
            DefaultJWTCallerPrincipal parsedToken = (DefaultJWTCallerPrincipal) jwtParser.parse(tokenCookie);

            // Pega informações do payload
            String nome = parsedToken.getClaim("nome"); // normalmente é o "upn" ou "sub"
            String email = parsedToken.getClaim("email");
            Collection<String> roles = parsedToken.getClaim("roles");

            dados = new UsuarioDTO(nome, email, roles);
            

        } catch (Exception e) {
           ExceptionUtil.throwException(400, "Token inválido");
        }
        
        return ResponseBuilder.ok(dados).build();
    }

    @POST
    @Path("/logout")
    public RestResponse<String> logout() {
        NewCookie cookie = new NewCookie(
            "jwt", "", "/", null, null, 0, true, true
        );

        // Use o builder da forma correta:
        return RestResponse.ResponseBuilder
                .ok("Executado com sucesso")
                .header("Set-Cookie", cookie)
                .cookie(cookie)
                .build();
    }


    @POST
    @Path("/cadastrarAluno")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<String> cadastrarAluno(CadastroUsuarioDTO cadastro) {
        if (cadastro == null || cadastro.getNome() == null || cadastro.getNome().isBlank()) {
            ExceptionUtil.throwException(400, "É necessário informar o nome do usuário.");
        }
        usuarioService.cadastrarUsuario(cadastro, Roles.ALUNO);
        return ResponseBuilder.ok("Usuario cadastrado como aluno com sucesso").build();
    }

    @POST
    @Path("/cadastrarProfessor")
    @PermitAll
    public RestResponse<String> cadastrarProfessor(@Valid CadastroUsuarioDTO cadastro) {
        usuarioService.cadastrarOutroUsuario(cadastro, Roles.PROFESSOR);
        return ResponseBuilder.ok("Usuario cadastrado como professor com sucesso").build();
    }

    @POST
    @Path("/cadastrarAdmin")
    @PermitAll
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
