package grupo1.aps2.controller;

import grupo1.aps2.exceptions.UnauthorizedUserException;
import grupo1.aps2.security.Roles;
import grupo1.aps2.service.UsuarioService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

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

    @POST
    @Path("/cadastrarAluno")
    @PermitAll
    public RestResponse<String> cadastrarAluno(@Valid CadastroUsuarioDTO cadastro, @HeaderParam("Authorization") String authHeader) {
        validateAuthHeader(authHeader);
        usuarioService.cadastrarUsuario(cadastro, Roles.ALUNO, authHeader);
        return ResponseBuilder.ok("Usuario cadastrado como aluno com sucesso").build();
    }

    @POST
    @Path("/cadastrarProfessor")
    @RolesAllowed({ "Admin" })
    public RestResponse<String> cadastrarProfessor(@Valid CadastroUsuarioDTO cadastro, @HeaderParam("Authorization") String authHeader) {
        validateAuthHeader(authHeader);
        usuarioService.cadastrarUsuario(cadastro, Roles.PROFESSOR, authHeader);
        return ResponseBuilder.ok("Usuario cadastrado como professor com sucesso").build();
    }

    @POST
    @Path("/cadastrarAdmin")
    @PermitAll
    public RestResponse<String> cadastrarAdmin(@Valid CadastroUsuarioDTO cadastro, @HeaderParam("Authorization") String authHeader) {
        System.out.println("Cadastro de administrador: " + authHeader);
        validateAuthHeader(authHeader);
        System.out.println("Cadastro de administrador: " + cadastro);

        usuarioService.cadastrarUsuario(cadastro, Roles.ADMIN, authHeader);
        return ResponseBuilder.ok("Usuario cadastrado como administrador com sucesso").build();
    }

    @POST
    @Path("/login")
    public RestResponse<String> login(@HeaderParam("Authorization") String authHeader) {
        System.out.println("Cadastro de administrador: " + authHeader);
        validateAuthHeader(authHeader);
        return ResponseBuilder.ok(usuarioService.logarUsuario(authHeader)).build();
    }

    private void validateAuthHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            throw new UnauthorizedUserException("Authorization header is missing or invalid.");
        }
    }
}
