package grupo1.aps2.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

import grupo1.aps2.service.JwtService;
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
import jakarta.annotation.security.PermitAll;
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

@ApplicationScoped
@Path("users")
public class UsuarioController {

    UsuarioService usuarioService;
    JwtService jwtService = new JwtService();

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
            String roles = parsedToken.getClaim("roles");

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
        DTOUsuario.AuthCredentials credenciais = parseBasicAuthorizationHeader(authHeader);
        usuarioService.cadastrarAdminBypass(cadastro, credenciais);
        return ResponseBuilder.ok("Usuario cadastrado como administrador com sucesso").build();
    }

    DTOUsuario.AuthCredentials parseBasicAuthorizationHeader(String authHeader) {
        try {
            // Remove "Basic " do início
            String base64Credentials = authHeader.substring("Basic ".length()).trim();

            // Decodifica Base64 para string "email:senha"
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);

            // Divide email e senha
            String[] values = credentials.split(":", 2);
            if (values.length != 2) {
                ExceptionUtil.throwException(401, MensagemErro.INVALID_AUTH_HEADER);
            }

            return new DTOUsuario.AuthCredentials(values[0], values[1]);
        } catch (IllegalArgumentException e) {
            ExceptionUtil.throwException(401, MensagemErro.INVALID_AUTH_HEADER);
            return null; // Nunca chega aqui
        }
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<?> login(@HeaderParam("Authorization") String authHeader) {
        // Verifica se o header existe e começa com "Basic "
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            ExceptionUtil.throwException(401, MensagemErro.INVALID_AUTH_HEADER);
        }

        // Decodifica o Basic Auth
        DTOUsuario.AuthCredentials credenciais = parseBasicAuthorizationHeader(authHeader);

        // Valida credenciais e gera token JWT
        String token = usuarioService.logarUsuario(credenciais); // Aqui o método deve validar email e senha

        if (token == null || token.isEmpty()) {
            ExceptionUtil.throwException(401, "Usuário ou senha inválidos");
        }

        // Busca dados do usuário
        UsuarioDTO usuario = usuarioService.buscarUsuarioPorEmail(credenciais.email());

        // Monta resposta com token e usuário
        var resposta = new java.util.HashMap<String, Object>();
        resposta.put("token", token);
        resposta.put("usuario", usuario);

        return RestResponse.ResponseBuilder.ok(resposta).build();
    }
}
